package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 22/06/16.
 */
public class ImportGeocodingInfo implements Executable{
    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters\n" +
                    "1. Database folder\n" +
                    "2. Folder including TSV files with Geocoding information");
        }else{

            try{

                String dbFolderSt = args[0];
                String inputFolderSt = args[1];

                File inputFolder = new File(inputFolderSt);

                if(!inputFolder.isDirectory()){

                    System.out.println("Please enter a valid folder name...");

                }else{

                    DatabaseManager manager = new DatabaseManager(dbFolderSt);
                    Transaction tx = manager.beginTransaction();

                    int installationsUpdatedCounter = 0;

                    System.out.println("Looping through files...");

                    File[] files = inputFolder.listFiles();

                    for(File file : files){

                        if(file.getName().endsWith(".tsv")){
                            System.out.println("Importing file: " + file.getName());

                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line;
                            reader.readLine();//header

                            while((line = reader.readLine()) != null){

                                String[] columns = line.split("\t");
                                String installationIdSt = columns[0];
                                String latitudeSt = columns[1];
                                String longitudeSt = columns[2];

                                Installation installation = manager.getInstallationById(installationIdSt);
                                if(installation != null){

                                    installation.setLatitude(latitudeSt);
                                    installation.setLongitude(longitudeSt);

                                    installationsUpdatedCounter++;

                                }else{

                                    AircraftOperator aircraftOperator = manager.getAircraftOperatorById(installationIdSt);

                                    if(aircraftOperator != null){

                                        installationsUpdatedCounter++;

                                        aircraftOperator.setLatitude(latitudeSt);
                                        aircraftOperator.setLongitude(longitudeSt);

                                    }else{
                                        System.out.println("The installation/aircraft operator with id: " + installationIdSt +
                                                            " could not be found in the database...");
                                    }
                                }

                                if(installationsUpdatedCounter % 100 == 0){
                                    System.out.println(installationsUpdatedCounter + " installations + aircraft operators updated");
                                    tx.success();
                                    tx.close();
                                    tx = manager.beginTransaction();
                                }

                            }

                            reader.close();
                        }
                    }

                    tx.success();
                    tx.close();
                    manager.shutdown();

                    System.out.println("Done!");

                }



            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
