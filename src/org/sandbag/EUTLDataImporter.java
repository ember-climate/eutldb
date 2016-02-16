package org.sandbag;

import org.neo4j.csv.reader.SourceTraceability;
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
            HashMap<String, Period> periodsMap = new HashMap<>();


            int lineCounter = 0;

            System.out.println("Reading installations...");

            Transaction tx = graphDb.beginTx();

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
                String typeSt = columns[9];
                String subTypeSt = columns[10];
                String periodSt = columns[11];
                String tonesCO2 = columns[12];

                if(installationKey != null){

                    Country country = null;
                    Company parentCompany = null;
                    Company accountHolder = null;
                    Sector sector = null;

                    if(countryName != null){
                        country = countriesMap.get(countryName);

                        if(country == null){

                            Node countryNode = graphDb.createNode(DynamicLabel.label(CountryModel.LABEL));
                            country = new Country(countryNode);
                            country.setName(countryName);
                            countriesMap.put(countryName, country);
                        }


                    }else {
                        System.out.println("No country found for installation: " + installationKey);
                    }

                    if(parentCompanySt != null && !parentCompanySt.isEmpty()){
                        parentCompany = companiesMap.get(parentCompanySt);
                        if(parentCompany == null){
                            Node companyNode = graphDb.createNode(DynamicLabel.label(CompanyModel.LABEL));
                            parentCompany = new Company(companyNode);
                            parentCompany.setName(parentCompanySt);
                            companiesMap.put(parentCompanySt, parentCompany);
                        }
                    }

                    if(accountHolderSt != null){
                        accountHolder = companiesMap.get(accountHolderSt);
                        if(accountHolder == null){
                            Node companyNode = graphDb.createNode(DynamicLabel.label(CompanyModel.LABEL));
                            accountHolder = new Company(companyNode);
                            accountHolder.setName(accountHolderSt);
                            companiesMap.put(accountHolderSt, accountHolder);
                            if(parentCompany != null){
                                accountHolder.setParentCompany(parentCompany);
                            }
                        }
                    }

                    if(sectorCategory != null){
                        sector = sectorsMap.get(sectorCategory);
                        if(sector == null){
                            Node sectorNode = graphDb.createNode(DynamicLabel.label(SectorModel.LABEL));
                            sector = new Sector(sectorNode);
                            sector.setName(sectorCategory);
                            sectorsMap.put(sectorCategory, sector);
                        }
                    }

                    Period period = null;

                    if(periodSt != null){
                        period = periodsMap.get(periodSt);
                        if(period == null){
                            Node periodNode = graphDb.createNode(DynamicLabel.label(PeriodModel.LABEL));
                            period = new Period(periodNode);
                            period.setName(periodSt);
                            periodsMap.put(periodSt, period);
                        }
                    }

                    Installation installation = installationsMap.get(installationKey);

                    if(installation == null){

                        Node installationNode = graphDb.createNode(DynamicLabel.label(InstallationModel.LABEL));

                        installation = new Installation(installationNode);
                        installation.setId(installationKey);
                        installation.setName(installationName);
                        installation.setOpen(installationOpen.equals("OPEN"));
                        installation.setCity(installationCity);
                        installation.setPostCode(installationPostCode);

                        if(sector != null){
                            installation.setSector(sector);
                        }
                        if(accountHolder != null){
                            installation.setCompany(accountHolder);
                        }
                        if(country != null){
                            installation.setCountry(country);
                        }

                        installationsMap.put(installationKey, installation);

                    }



                    if(typeSt.trim().equals("Emissions")){

                        if(subTypeSt.equals("EM Verified")){
                            if(period != null){
                                //System.out.println("Storing emissions info!");
                                double tempValue;
                                try{
                                    tempValue = Double.parseDouble(tonesCO2.replaceAll(",",""));
                                    installation.setVerifiedEmissionsForPeriod(period, tempValue);
                                }catch (Exception e){
                                    System.out.println("Problem parsing CO2 value: " + tonesCO2 + " for line:\n" + line);
                                }
                            }
                        }

                    }



                }else {
                    System.out.println("Installation found with no key: " + line);
                }


                if(lineCounter % 1000 == 0){
                    System.out.println(lineCounter + " lines parsed...");
                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();
                }

            }

            tx.success();
            tx.close();

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
