package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.FuelType;
import org.sandbag.model.nodes.Installation;
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

                int lineCounter = 0;

                System.out.println("Reading file...");

                reader.readLine(); //skipping the header of the file

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");

                    String installationIdSt = columns[0].trim().replace(" ","");
                    String fuelTypeSt = columns[1].trim();
                    String noteSt = columns[2].trim();
                    String sourceSt = columns[3].trim();

                    Installation installation = databaseManager.getInstallationById(installationIdSt);

                    String[] fuelTypeList = fuelTypeSt.split(",");

                    for (String currentFuelType : fuelTypeList){

                        String currentFuelTypeSt = currentFuelType.trim().toLowerCase();

                        if(!currentFuelTypeSt.isEmpty() && currentFuelTypeSt.indexOf("?") < 0){

                            FuelType fuelType = databaseManager.getFuelType(currentFuelTypeSt);

                            if(fuelType == null){
                                fuelType = databaseManager.createFuelType(currentFuelTypeSt);
                                tx.success();
                                tx.close();
                                tx = databaseManager.beginTransaction();
                            }

                            if(installation != null){

                                installation.setFuelType(fuelType, noteSt, sourceSt);

                            }else{
                                AircraftOperator aircraftOperator = databaseManager.getAircraftOperatorById(installationIdSt);
                                if(aircraftOperator != null){

                                    aircraftOperator.setFuelType(fuelType, noteSt, sourceSt);

                                }else{
                                    System.out.println("No installation or aircraft operator could be found for the id: "
                                            + installationIdSt);
                                }
                            }
                        }

                    }

                    lineCounter++;

                    if(lineCounter % 100 == 0){
                        System.out.println(lineCounter + " lines already processed...");
                        tx.success();
                        tx.close();
                        tx = databaseManager.beginTransaction();
                    }


                }

                tx.success();
                tx.close();

                reader.close();

                databaseManager.shutdown();

                System.out.println("Done! :)");

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
