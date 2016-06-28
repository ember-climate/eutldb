package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 28/06/16.
 */
public class ImportCountriesCoordinates implements Executable{

    public static void main(String[] args){
        if (args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Coordinates TSV file");
        }else{

            String databaseFolderSt = args[0];
            String coordinatesFileSt = args[1];

            try{

                System.out.println("Opening database...");

                DatabaseManager manager = new DatabaseManager(databaseFolderSt);

                Transaction tx = manager.beginTransaction();

                BufferedReader reader = new BufferedReader(new FileReader(new File(coordinatesFileSt)));
                String line;
                reader.readLine(); //header

                System.out.println("Reading coordinates file...");

                while((line = reader.readLine()) != null){

                    String columns[] = line.split("\t");

                    String countryIdSt = columns[0].trim();
                    double centerLat = Double.parseDouble(columns[1].trim());
                    double centerLg = Double.parseDouble(columns[2].trim());
                    double bbMaxLat = Double.parseDouble(columns[3].trim());
                    double bbMinLat = Double.parseDouble(columns[4].trim());
                    double bbMaxLg = Double.parseDouble(columns[5].trim());
                    double bbMinLg = Double.parseDouble(columns[6].trim());

                    Country country = manager.getCountryById(countryIdSt);
                    if(country != null){

                        country.setCenterLatitude(centerLat);
                        country.setCenterLongitude(centerLg);
                        country.setBoundingBoxMaxLatitude(bbMaxLat);
                        country.setBoundingBoxMinLatitude(bbMinLat);
                        country.setBoundingBoxMaxLongitude(bbMaxLg);
                        country.setBoundingBoxMinLongitude(bbMinLg);

                    }else{
                        System.out.println("The country with ID: " + countryIdSt + " could not be found...");
                    }

                }

                reader.close();

                tx.success();
                tx.close();

                manager.shutdown();

                System.out.println("Done!");


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
