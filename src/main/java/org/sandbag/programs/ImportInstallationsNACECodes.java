package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.NACECode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 27/04/16.
 */
public class ImportInstallationsNACECodes {

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Installations <-> NACE code file (.tsv)");
        }else{

            String dbFolder = args[0];
            String fileSt = args[1];

            DatabaseManager databaseManager = new DatabaseManager(dbFolder);
            Transaction tx = databaseManager.beginTransaction();

            try {

                System.out.println("Reading file...");

                int lineCounter = 0;

                BufferedReader reader = new BufferedReader(new FileReader(new File(fileSt)));
                String line;
                reader.readLine();//skipping the header

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");
                    String countryIdSt = columns[0].trim();
                    String installationIdSt = columns[3].trim();
                    String naceCodeSt = columns[4].trim();

                    String installationCompleteID = countryIdSt + installationIdSt;

                    Installation installation = databaseManager.getInstallationById(installationCompleteID);

                    if(installation != null){

                        NACECode naceCode = databaseManager.getNACECodeById(naceCodeSt);
                        if(naceCode == null){
                            System.out.println("Creating NACE code with id: " + naceCodeSt);
                            naceCode = databaseManager.createNACECode(naceCodeSt, "");
                            tx.success();
                            tx.close();
                            tx = databaseManager.beginTransaction();
                        }


                        installation.setNACECode(naceCode);

                    }else{
                        System.out.println("The installation with id: " + installationCompleteID + " could not be found... :(");
                    }

                    lineCounter++;

                    if(lineCounter % 100 == 0){
                        tx.success();
                        tx.close();
                        tx = databaseManager.beginTransaction();
                        System.out.println(lineCounter + " installations updated...");
                    }

                }

                tx.success();
                tx.close();

                reader.close();
                databaseManager.shutdown();

                System.out.println("Finished!");

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
