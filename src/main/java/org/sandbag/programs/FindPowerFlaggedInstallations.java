package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.NACECode;
import org.sandbag.model.relationships.AllowancesInAllocation;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 29/04/16.
 */
public class FindPowerFlaggedInstallations {

    public static String[] POWER_FLAG_NACE_CODES = {"35.00", "35.10", "35.11", "35.12", "35.13", "35.14", "35.30"};

    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder");
        }else{

            String dbFolder = args[0];

            Set<String> powerFlagNaceCodesSet = new HashSet<>();
            for( String value : POWER_FLAG_NACE_CODES){
                powerFlagNaceCodesSet.add(value);
            }

            DatabaseManager databaseManager = new DatabaseManager(dbFolder);
            Transaction tx = databaseManager.beginTransaction();

            Iterator<Node> iterator = databaseManager.findNodes(DatabaseManager.INSTALLATION_LABEL);

            System.out.println("Looping through installations...");

            int installationCounter = 0;

            while(iterator.hasNext()){
                Installation installation = new Installation(iterator.next());

                NACECode naceCode = installation.getNACECode();

                boolean alreadyPowerFlagged = false;

                if(naceCode != null){
                    String naceCodeIdSt = installation.getNACECode().getId();
                    System.out.println("naceCodeIdSt = " + naceCodeIdSt);
                    if(powerFlagNaceCodesSet.contains(naceCodeIdSt)){
                        installation.setPowerFlag("true");
                        alreadyPowerFlagged = true;
                        System.out.println("hola!");
                    }
                }

                if(!alreadyPowerFlagged){
                    List<AllowancesInAllocation> list = installation.getAllowancesInAllocationByType(AllowancesInAllocationModel.ARTICLE_10C_TYPE);
                    if(list.size() > 0){
                        installation.setPowerFlag("true");
                    }else{
                        installation.setPowerFlag("false");
                    }
                }

                installationCounter++;

                if(installationCounter % 100 == 0){
                    System.out.println(installationCounter + " installations analyzed so far...");
                    tx.success();
                    tx.close();
                    tx = databaseManager.beginTransaction();
                }
            }


            tx.success();
            tx.close();

            databaseManager.shutdown();

            System.out.println("Finished!");


        }

    }
}
