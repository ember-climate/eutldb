package org.sandbag.programs;

import org.sandbag.model.DatabaseManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 14/04/16.
 */
public class ImportSandbagSectorsAggregation {

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Sandbag Sectors aggregation file (.csv)");
        }else{

            String dbFolder = args[0];
            String fileSt = args[1];

            DatabaseManager databaseManager = new DatabaseManager(dbFolder);

            try {

                BufferedReader reader = new BufferedReader(new FileReader(new File(fileSt)));
                String line;

                while((line = reader.readLine()) != null){
                    String[] columns = line.split(",");
                    String sectorIdSt = columns[0];
                    String sandbagSectorNameSt = columns[2];

                }

                reader.close();
                databaseManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
