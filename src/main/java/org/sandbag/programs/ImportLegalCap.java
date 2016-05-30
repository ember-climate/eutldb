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
 * Created by root on 23/05/16.
 */
public class ImportLegalCap implements Executable {

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters\n" +
                    "1. Database folder\n" +
                    "2. Input TSV Legal Cap data file");
        }else{

            String dbFolder = args[0];
            String legalCapFileSt = args[1];

            try{

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                BufferedReader reader = new BufferedReader(new FileReader(new File(legalCapFileSt)));

                String line;

                reader.readLine(); //skig header

                Country euCountry = databaseManager.getCountryById("EU");
                if(euCountry == null){
                    System.out.println("Creating country for the European Union...");
                    euCountry = databaseManager.createCountry("European Union", "EU");
                    tx.success();
                    tx.close();
                    tx = databaseManager.beginTransaction();
                }else{
                    System.out.println("EU country found");
                }

                System.out.println("Reading file...");

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");

                    String dataSourceSt = columns[0].trim();
                    String periodSt = columns[1].trim();
                    String amountSt = columns[2].trim();

                    Period period = databaseManager.getPeriodByName(periodSt);

                    if(period != null){

                        euCountry.setLegalCap(period, Double.parseDouble(amountSt), dataSourceSt);

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
}
