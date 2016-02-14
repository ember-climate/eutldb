package org.sandbag;

import org.neo4j.cypher.internal.compiler.v2_2.planner.logical.plans.Strictness;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.sandbag.model.Country;
import org.sandbag.model.Installation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class EUTLDataImporter {

    private static GraphDatabaseService graphDb;

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

            int lineCounter = 0;

            System.out.println("Reading installations...");

            while((line = reader.readLine()) != null ){

                lineCounter++;

                String[] columns = line.split(",");
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

                    Installation tempInstallation = new Installation();
                    tempInstallation.setId(installationKey);
                    tempInstallation.setName(installationName);
                    tempInstallation.setOpen(installationOpen.equals("OPEN"));
                    Country tempCountry = new Country();
                    tempCountry.setName(countryName);
                    tempInstallation.setCountry(tempCountry);
                    tempInstallation.setCity(installationCity);
                    tempInstallation.setPostCode(installationPostCode);
                    installationsMap.put(installationKey, tempInstallation);

                }

            }


            reader.close();

            System.out.println("Storing installations...");

            Transaction tx = graphDb.beginTx();
            int installationCounter = 0;

            for(Installation installation : installationsMap.values()){

                createInstallation(installation);
                installationCounter++;

                if(installationCounter % 100 == 0){
                    System.out.println(installationCounter + " installations stored...");
                    tx.success();
                    tx = graphDb.beginTx();
                }
            }

            tx.success();

            System.out.println("Done!");
            System.out.println("There were " + lineCounter + " lines parsed");

            graphDb.shutdown();

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void initDatabase(String dbFolder){
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( new File(dbFolder) );
    }


    private void createInstallation(Installation installation){
        Node installationNode = graphDb.createNode();
        installationNode.setProperty( "id", installation.getId() );
        installationNode.setProperty( "name", installation.getName() );
        installationNode.setProperty( "city", installation.getCity() );
        installationNode.setProperty( "post_code", installation.getPostCode() );
    }

}
