package org.sandbag.eutldb.tests;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.unsafe.impl.batchimport.input.csv.Data;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.*;
import org.sandbag.model.relationships.OffsetProject;

import java.util.Iterator;

/**
 * Created by root on 30/03/16.
 */
public class DBTests {

    public static void main(String[] args){

        DatabaseManager manager = new DatabaseManager("/home/pablo/sandbag/EUTL_data/eutldb");

        Transaction tx = manager.beginTransaction();

        //Country country = manager.getCountryByName("Belgium");
//        Country countryHola = manager.createCountry("hola", "hola");
//        Country country = manager.getCountryById("BE");
//
//        if(country != null){
//            System.out.println("country.getName() = " + country.getName());
//            System.out.println("country.getId() = " + country.getId());
//        }else{
//            System.out.println("Country not found");
//        }
//
//        Period period = manager.createPeriod("2005");
//        if(period != null){
//            System.out.println("Period found!");
//        }else{
//            System.out.println("nothing was found... :(");
//        }
//
//        Project project = manager.createProject("helloooo");
//        if(project != null){
//            System.out.println("Project found!");
//        }else{
//            System.out.println("nothing was found... :(");
//        }


        System.out.println("hi!");

//        Project project = manager.createProject("1");
//        tx.success();
//        tx.close();
//
//        tx = manager.beginTransaction();
//
//        project = manager.getProjectById("1000292");
//        Iterator<Node> iterator = manager.findNodes(DatabaseManager.PROJECT_LABEL);
//        while(iterator.hasNext()){
//            project = new Project(iterator.next());
//            System.out.println("project.getId() = " + project.getId());
//        }

//        SandbagSector sector = manager.createSandbagSector("a", "AA");
//        tx.success();
//        tx.close();
//
//        tx = manager.beginTransaction();
        SandbagSector sector1 = manager.getSandbagSectorById("44");
        System.out.println("sector1.getName() = " + sector1.getName());



        tx.success();
        tx.close();

        manager.shutdown();
    }
}
