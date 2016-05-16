package org.sandbag.programs;

import org.neo4j.cypher.internal.compiler.v1_9.commands.expressions.Count;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 16/05/16.
 */
public class ImportAuctionData {

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters\n" +
                    "1. Database folder\n" +
                    "2. Input CSV Auction data folder");
        }else{

            String dbFolder = args[0];
            String fileSt = args[1];

            try{

                File file = new File(fileSt);
                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.readLine();//skipping the header

                String line;

                System.out.println("reading file...");

                while((line = reader.readLine()) != null){
                    String[] columns = line.split("\t");

                    String countryIdst = columns[0].trim().split(" ")[0];
                    String periodSt = columns[3].trim();
                    String amountSt = columns[4].trim();
                    String sourceSt = columns[5].trim();

                    Country country = databaseManager.getCountryById(countryIdst);
                    if(country != null){

                        Period period = databaseManager.getPeriodByName(periodSt);

                        if(period != null){

                            country.setAuctionedForPeriod(period, amountSt, sourceSt);

                        }else{
                            System.out.println("Period: " + periodSt + " could not be found...");
                            System.out.println("Data won't be stored for country: " + countryIdst + " and the aforementioned period :(");
                        }

                    }else{
                        System.out.println("The country with id: " + countryIdst + " could not be found...");
                        System.out.println("No information was stored for it :(");
                    }


                }

                reader.close();
                tx.success();
                tx.close();
                databaseManager.shutdown();

                System.out.println("Done! :)");

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
