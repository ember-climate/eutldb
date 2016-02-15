package org.sandbag;

import org.neo4j.cypher.internal.compiler.v2_2.planner.logical.plans.Strictness;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.sandbag.model.Country;
import org.sandbag.model.CountryModel;
import org.sandbag.model.Installation;
import org.sandbag.model.InstallationModel;

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

            int lineCounter = 0;

            System.out.println("Reading installations...");

            while((line = reader.readLine()) != null ){

                lineCounter++;

                String[] columns = line.split("\t");
                String countryName = columns[0];
                String parentCompany = columns[1];
                String accountHolder = columns[2];
                String installationName = columns[3];
                String installationKey = columns[4];
                String installationCity = columns[5];
                String installationPostCode = columns[6];
                String installationOpen = columns[7];
                String sectorCategory = columns[8];

                Installation tempValue = installationsMap.get(installationKey);

                if(tempValue == null){

                    Node installationNode = graphDb.createNode(DynamicLabel.label(InstallationModel.LABEL));

                    Installation tempInstallation = new Installation(installationNode);
                    tempInstallation.setId(installationKey);
                    tempInstallation.setName(installationName);
                    tempInstallation.setOpen(installationOpen.equals("OPEN"));
                    tempInstallation.setCity(installationCity);
                    tempInstallation.setPostCode(installationPostCode);

                    Country tempCountry = countriesMap.get(countryName);

                    if(tempCountry == null){
                        Node countryNode = graphDb.createNode(DynamicLabel.label(CountryModel.LABEL));
                        tempCountry.setName(countryName);
                        tempCountry = new Country(countryNode);
                        countriesMap.put(countryName, tempCountry);
                    }

                    tempInstallation.setCountry(tempCountry);

                    installationsMap.put(installationKey, tempInstallation);

                }

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
