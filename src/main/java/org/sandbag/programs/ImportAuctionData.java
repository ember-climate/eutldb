package org.sandbag.programs;

import org.sandbag.model.DatabaseManager;

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

                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.readLine();//skipping the header

                String line;

                while((line = reader.readLine()) != null){
                    String[] columns = line.split("\t");

                    String countryIdst = columns[0].trim().split(" ")[0];
                    String periodSt = columns[3].trim();
                    String amountSt = columns[4].trim();
                    String sourceSt = columns[5].trim();


                }

                reader.close();
                databaseManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
