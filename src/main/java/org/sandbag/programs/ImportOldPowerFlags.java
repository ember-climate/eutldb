package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 06/05/16.
 */
public class ImportOldPowerFlags implements Executable{

    public void execute(List<String> list){
        main(list.toArray(new String[0]));
    }

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Power flag file");
        }else{

            String dbFolder = args[0];
            String fileSt = args[1];

            try{

                System.out.println("Creating database manager...");

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                System.out.println("Reading file...");

                File file = new File(fileSt);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                //skip header
                reader.readLine();

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");
                    String keySt = columns[0].trim().replace(" ","");
                    String reasonSt = columns[2].trim();

                    Installation installation = databaseManager.getInstallationById(keySt);
                    if(installation != null){

                        installation.setPowerFlag("true");
                        installation.setPowerFlagReason(reasonSt);

                    }else{
                        System.out.println("The installation with id " + keySt + " could not be found... :(");
                    }

                }

                tx.success();
                tx.close();

                reader.close();
                databaseManager.shutdown();

                System.out.println("Done! :)");

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
