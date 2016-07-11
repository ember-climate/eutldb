package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 08/07/16.
 */
public class ExportCoordinates implements Executable {

    public static String HEADER = "Installationn/Aircraft Operator ID\tLatitude\tLongitude";
    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Database folder\n" +
                    "2. Output TSV file");
        }else{

            try{

                String dbFolderSt = args[0];
                String outFileSt = args[1];

                DatabaseManager databaseManager = new DatabaseManager(dbFolderSt);
                Transaction tx = databaseManager.beginTransaction();
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileSt)));
                writer.write(HEADER + "\n");

                Iterator<Node> iterator = databaseManager.graphDb.findNodes(DatabaseManager.INSTALLATION_LABEL);

                System.out.println("Looping through installations...");
                while(iterator.hasNext()){

                    Installation installation = new Installation(iterator.next());
                    writer.write(installation.getId() + "\t" + installation.getLatitude() + "\t" +
                            installation.getLongitude() + "\n");

                }

                iterator = databaseManager.graphDb.findNodes(DatabaseManager.AIRCRAFT_OPERATOR_LABEL);

                System.out.println("Looping through aircraft operators...");
                while(iterator.hasNext()){

                    AircraftOperator aircraftOperator = new AircraftOperator(iterator.next());
                    writer.write(aircraftOperator.getId() + "\t" + aircraftOperator.getLatitude() + "\t" +
                            aircraftOperator.getLongitude() + "\n");

                }

                writer.close();

                tx.success();
                tx.close();

                databaseManager.shutdown();

                System.out.println("Done!");

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
