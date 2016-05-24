package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 20/05/16.
 */
public class ImportOffsets2013Onwards implements Executable{

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Database folder \n" +
                    "2. Offsets TSV file");
        }else{

            String dbFolder = args[0];
            String offsetsFileSt = args[1];

            try{

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                BufferedReader reader = new BufferedReader(new FileReader(new File(offsetsFileSt)));

                String line;

                reader.readLine(); //skig header

                Country euCountry = databaseManager.getCountryById("EU");
                if(euCountry == null){
                    System.out.println("Creating country for the European Union...");
                    euCountry = databaseManager.createCountry("European Union", "EU");
                    tx.success();
                    tx.close();
                    tx = databaseManager.beginTransaction();
                }

                System.out.println("Reading file...");

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");

                    String offsetTypeSt = columns[2].trim();
                    String periodSt = columns[3].trim();
                    String amountSt = columns[4].trim();
                    String referenceSt = columns[5].trim();

                    Period period = databaseManager.getPeriodByName(periodSt);

                    if(period != null){
                        databaseManager.createOffset2013Onwards(amountSt,offsetTypeSt,euCountry,period);
                    }else{
                        System.out.println("The period " + periodSt + " could not be found... :(");
                    }

                }

                reader.close();

                tx.success();
                tx.close();

                databaseManager.shutdown();


                System.out.println("Done!!");

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
