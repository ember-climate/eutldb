package org.sandbag.eutldb.tests;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 30/03/16.
 */
public class DBTests {

    public static void main(String[] args){

        DatabaseManager manager = new DatabaseManager("/home/pablo/sandbag/EUTL_data/eutldb");

        Transaction tx = manager.beginTransaction();

        //Country country = manager.getCountryByName("Belgium");
        Country countryHola = manager.createCountry("hola", "hola");
        Country country = manager.getCountryById("BE");

        if(country != null){
            System.out.println("country.getName() = " + country.getName());
            System.out.println("country.getId() = " + country.getId());
        }else{
            System.out.println("Country not found");
        }

        Period period = manager.createPeriod("2005");
        if(period != null){
            System.out.println("Period found!");
        }else{
            System.out.println("nothing was found... :(");
        }



        tx.success();
        tx.close();

        manager.shutdown();
    }
}
