package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.relationships.AllowancesInAllocation;
import org.sandbag.util.Executable;

import java.io.*;
import java.util.List;

/**
 * Created by root on 15/07/16.
 */
public class Import2012AllowancesCorrectionsForAviation implements Executable{

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Input TSV file");
        }else{

            String dbFolder = args[0];
            String inputFileSt = args[1];

            try{

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Import2012AviationCorrectionsErrors.txt")));

                BufferedReader reader = new BufferedReader(new FileReader(new File(inputFileSt)));
                String line;
                reader.readLine();//header

                System.out.println("Looping through aircraft operators...");

                int totalAllowancesNotFixed = 0;

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");

                    String uniqueCodeComissionSt = columns[0].trim();
                    String operatorNameSt = columns[1].trim();
                    String countrySt = columns[2].trim();
                    String allowancesReturnedSt = columns[3].trim();

                    //System.out.println("uniqueCodeComissionSt = " + uniqueCodeComissionSt);

                    Integer allowancesReturned = Integer.parseInt(allowancesReturnedSt);

                    AircraftOperator aircraftOperator = null;

                    if(uniqueCodeComissionSt.equals("8352")){
                        //bug with duplicated airline record, this is a specific fix for it
                        aircraftOperator = databaseManager.getAircraftOperatorById("GB202850");
                    }else if(uniqueCodeComissionSt.equals("27210")){
                        //bug with duplicated airline record, this is a specific fix for it
                        aircraftOperator = databaseManager.getAircraftOperatorById("GB201549");
                    }else if(uniqueCodeComissionSt.equals("32631")){
                        //bug with duplicated airline record, this is a specific fix for it
                        aircraftOperator = databaseManager.getAircraftOperatorById("NL202833");
                    }else{
                        System.out.println("uniqueCodeComissionSt = " + uniqueCodeComissionSt);
                        aircraftOperator =
                                databaseManager.getAircraftOperatorByUniqueCodeUnderCommissionRegulation(uniqueCodeComissionSt);
                    }

                    if(aircraftOperator != null){

                        AllowancesInAllocation allowances = aircraftOperator.getAllowancesInAllocationForPeriod(databaseManager.getPeriodByName("2012"));
                        double oldValue = allowances.getValue();
                        double newValue = oldValue - allowancesReturned;
                        allowances.setValue(newValue);

                    }else{
                        System.out.println("The aircraft operator with id: " + uniqueCodeComissionSt +
                                " could not be found... ( " + operatorNameSt + " )");
                        writer.write("Operator with id: " + uniqueCodeComissionSt + " could not be found\n");
                        totalAllowancesNotFixed += allowancesReturned;
                    }

                }

                System.out.println("A total of " + totalAllowancesNotFixed + " could not be corrected due to various reasons...");
                System.out.println("Done!");

                reader.close();
                writer.close();

                tx.success();
                tx.close();
                databaseManager.shutdown();

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
