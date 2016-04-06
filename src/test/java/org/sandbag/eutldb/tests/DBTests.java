package org.sandbag.eutldb.tests;

import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.nodes.Project;
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

        Project project = manager.getProjectById("1000292");
        if(project != null){
            Iterator<Relationship> iterator = project.getOffsetProject();
            while(iterator.hasNext()){
                Offset offset = new OffsetProject(iterator.next()).getOffset();
                System.out.println("Offset amount: " + offset.getAmount());
            }
        }else{
            System.out.println("Project not found!");
        }


        tx.success();
        tx.close();

        manager.shutdown();
    }
}
