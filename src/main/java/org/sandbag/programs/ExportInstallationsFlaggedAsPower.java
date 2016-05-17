package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 10/05/16.
 */
public class ExportInstallationsFlaggedAsPower implements Executable {

    @Override
    public void execute(List<String> args) {
        System.out.println(args.toArray(new String[0]));
        main(args.toArray(new String[0]));
    }

    public static String HEADER = "Country\tInstallation ID\tPower flag reason";

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Output TSV file");
        }else{

            String dbFolder = args[0];
            String outputFileSt = args[1];

            try{

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);
                Transaction tx = databaseManager.beginTransaction();

                File outFile = new File(outputFileSt);
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                writer.write(HEADER + "\n");

                Iterator<Node> iterator = databaseManager.findNodes(DatabaseManager.INSTALLATION_LABEL);

                System.out.println("Looping through installations...");

                while(iterator.hasNext()){
                    Installation installation = new Installation(iterator.next());
                    if(installation.getPowerFlag().equals("true")){
                        writer.write(installation.getCountry().getName() + "\t" + installation.getId() + "\t" +
                                installation.getPowerFlagReason() + "\n");

                    }
                }

                tx.success();
                tx.close();


                writer.close();

                databaseManager.shutdown();

                System.out.println("Done! :)");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
