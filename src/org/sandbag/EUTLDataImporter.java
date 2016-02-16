package org.sandbag;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.kernel.impl.api.CommandApplierFacade;
import org.sandbag.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class EUTLDataImporter {

    private static GraphDatabaseService graphDb;
    private static Schema schema;

    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("This program expects the following parameters:" +
                    "\n1. Database folder" +
                    "\n2. Input CSV file");
        }else{

            String dbFolder = args[0];
            String csvFileSt = args[1];

            File csvFile = new File(csvFileSt);

            EUTLDataImporter importer = new EUTLDataImporter();
            importer.importDBFromCSVFile(dbFolder, csvFile);
        }

    }

    private void importDBFromCSVFile(String dbFolder, File csvFile){

        initDatabase(dbFolder);

        try{

            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line;

            reader.readLine(); //skipping the header

            HashMap<String,Installation> installationsMap = new HashMap<>();
            HashMap<String,Country> countriesMap = new HashMap<>();
            HashMap<String, Sector> sectorsMap = new HashMap<>();
            HashMap<String, Company> companiesMap = new HashMap<>();

            int lineCounter = 0;

            System.out.println("Reading installations...");

            try(Transaction tx = graphDb.beginTx()){

                while((line = reader.readLine()) != null ){

                    lineCounter++;

                    String[] columns = line.split("\t");
                    String countryName = columns[0];
                    String parentCompanySt = columns[1];
                    String accountHolderSt = columns[2];
                    String installationName = columns[3];
                    String installationKey = columns[4];
                    String installationCity = columns[5];
                    String installationPostCode = columns[6];
                    String installationOpen = columns[7];
                    String sectorCategory = columns[8];

                    if(installationKey != null){

                        Installation tempValue = installationsMap.get(installationKey);

                        if(tempValue == null){

                            Node installationNode = graphDb.createNode(DynamicLabel.label(InstallationModel.LABEL));

                            Installation tempInstallation = new Installation(installationNode);
                            tempInstallation.setId(installationKey);
                            tempInstallation.setName(installationName);
                            tempInstallation.setOpen(installationOpen.equals("OPEN"));
                            tempInstallation.setCity(installationCity);
                            tempInstallation.setPostCode(installationPostCode);

                            if(countryName != null){
                                Country tempCountry = countriesMap.get(countryName);

                                if(tempCountry == null){

                                    Node countryNode = graphDb.createNode(DynamicLabel.label(CountryModel.LABEL));
                                    tempCountry = new Country(countryNode);
                                    tempCountry.setName(countryName);
                                    countriesMap.put(countryName, tempCountry);
                                }

                                tempInstallation.setCountry(tempCountry);
                            }else {
                                System.out.println("No country found for installation: " + installationKey);
                            }

                            Company parentCompany = null;
                            if(parentCompanySt != null){
                                parentCompany = companiesMap.get(parentCompanySt);
                                if(parentCompany == null){
                                    Node companyNode = graphDb.createNode(DynamicLabel.label(CompanyModel.LABEL));
                                    parentCompany = new Company(companyNode);
                                    parentCompany.setName(parentCompanySt);
                                    companiesMap.put(parentCompanySt, parentCompany);
                                }
                            }

                            if(accountHolderSt != null){
                                Company accountHolder = companiesMap.get(accountHolderSt);
                                if(accountHolder == null){
                                    Node companyNode = graphDb.createNode(DynamicLabel.label(CompanyModel.LABEL));
                                    accountHolder = new Company(companyNode);
                                    accountHolder.setName(accountHolderSt);
                                    companiesMap.put(accountHolderSt, accountHolder);
                                    if(parentCompany != null){
                                        accountHolder.setParentCompany(parentCompany);
                                    }
                                }
                                tempInstallation.setCompany(accountHolder);
                            }

                            if(sectorCategory != null){
                                Sector sector = sectorsMap.get(sectorCategory);
                                if(sector == null){
                                    Node sectorNode = graphDb.createNode(DynamicLabel.label(SectorModel.LABEL));
                                    sector = new Sector(sectorNode);
                                    sector.setName(sectorCategory);
                                    sectorsMap.put(sectorCategory, sector);
                                }
                                tempInstallation.setSector(sector);
                            }


                            installationsMap.put(installationKey, tempInstallation);

                        }

                    }else {
                        System.out.println("Installation found with no key: " + line);
                    }


                }

                tx.success();
            }

            reader.close();

            System.out.println("There were " + lineCounter + " lines parsed");

            graphDb.shutdown();

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void initDatabase(String dbFolder){

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( new File(dbFolder) );

        try {

            try(Transaction tx = graphDb.beginTx()){

                System.out.println("Creating indices...");

                schema = graphDb.schema();
                IndexDefinition indexDefinition = schema.indexFor(DynamicLabel.label(Installation.LABEL))
                        .on(InstallationModel.id)
                        .create();

                tx.success();

                System.out.println("Done!");
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
