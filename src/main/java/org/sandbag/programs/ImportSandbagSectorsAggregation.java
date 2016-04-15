package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.SandbagSector;
import org.sandbag.model.nodes.Sector;

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
            Transaction tx = databaseManager.beginTransaction();

            try {

                System.out.println("Reading file...");

                BufferedReader reader = new BufferedReader(new FileReader(new File(fileSt)));
                String line;
                reader.readLine();//skipping the header

                while((line = reader.readLine()) != null){
                    String[] columns = line.split(",");
                    String sectorIdSt = columns[0].trim();
                    String sandbagSectorNameSt = columns[2].trim();
                    String sandbagSectorIdSt = columns[1].trim();

                    Sector sector = databaseManager.getSectorById(sectorIdSt);
                    if(sector != null){
                        SandbagSector sandbagSector = databaseManager.getSandbagSectorById(sandbagSectorIdSt);

                        if(sandbagSector == null){

                            System.out.println("Creating sector with id: " + sandbagSectorIdSt);
                            sandbagSector = databaseManager.createSandbagSector(sectorIdSt,sandbagSectorNameSt);
                            tx.success();
                            tx.close();
                            tx = databaseManager.beginTransaction();

                            sandbagSector.setAggregatesSector(sector);

                        }else{

                            System.out.println("Sandbag sector with id " + sandbagSectorIdSt + " found :)");

                        }


                    }else{
                        System.out.println("The sector with id: " + sectorIdSt + " could not be found... :(");
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
