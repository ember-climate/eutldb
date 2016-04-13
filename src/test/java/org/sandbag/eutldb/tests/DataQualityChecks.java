package org.sandbag.eutldb.tests;

import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 13/04/16.
 */
public class DataQualityChecks {

    protected static DatabaseManager DBMANAGER;

    public static void main(String[] args){

        String dbFolder = args[0];
        String registryFileSt = args[1];

        DataQualityChecks dataQualityChecks = new DataQualityChecks(dbFolder);

        try {
            dataQualityChecks.checkDataQualityWithETSRegistryFile(new File(registryFileSt));
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void checkDataQualityWithETSRegistryFile(File file) throws Exception{

        System.out.println("Starting quality check...");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        reader.readLine();//skipping header

        while((line = reader.readLine()) != null){
            String[] columns = line.split(",");
            String countryId = columns[0].trim();
            String installationId = columns[1].trim();

            //System.out.println("installationId = " + installationId);

            String completeId = countryId + installationId;

            Installation installation = DBMANAGER.getInstallationById(completeId);
            if(installation == null){
                AircraftOperator aircraftOperator = DBMANAGER.getAircraftOperatorById(completeId);
                if(aircraftOperator == null){
                    System.out.println("Installation/Aircraft Operator with id: " + completeId + " could not be found in the database... :(");
                }
            }
        }

        reader.close();

        System.out.println("Done!");
    }

    public DataQualityChecks(String dbFolder){
        DBMANAGER = new DatabaseManager(dbFolder);
    }
}
