package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 18/05/16.
 */
public class ImportFuelType implements Executable{

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder" +
                    "2. Fuel type CSV file");
        }else{

            String dbFolder = args[0];
            String fueltTypeFile = args[1];

            try{

                BufferedReader reader = new BufferedReader(new FileReader(new File(fueltTypeFile)));
                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                String line;

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");


                }

                tx.success();
                tx.close();

                reader.close();

                databaseManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }
}
