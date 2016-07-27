package org.sandbag.programs;

import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.*;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationModel;
import org.sandbag.model.relationships.interfaces.LegalCapModel;
import org.sandbag.model.relationships.interfaces.OffsetsModel;
import org.sandbag.model.relationships.interfaces.VerifiedEmissionsModel;
import org.sandbag.util.Executable;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 27/07/16.
 */
public class PrecomputeEUWideValues implements Executable {

    @Override
    public void execute(List<String> args) {

    }

    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder");
        }else{

            String dbFolder = args[0];

            try{

                DatabaseManager databaseManager = new DatabaseManager(dbFolder);

                Transaction tx = databaseManager.beginTransaction();

                System.out.println("Getting European Union country...");
                Country euCountry = databaseManager.getCountryById("EU");
                if(euCountry != null){

                    //**********************************************************//
                    //****************** VERIFIED EMISSIONS ********************//
                    System.out.println("Calculating verified emissions...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    Result allResult = databaseManager.graphDb.execute( "MATCH ()-[ve:VERIFIED_EMISSIONS]->(p:PERIOD) " +
                            "RETURN sum(ve.value) AS Verified_Emissions, p.name AS Period ORDER BY p.name");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double emissions = Double.parseDouble(String.valueOf(row.get( "Verified_Emissions" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setVerifiedEmissionsEUWide(period, emissions, VerifiedEmissionsEUWide.EMISSIONS_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    Result aviationResult = databaseManager.graphDb.execute( "MATCH (ao:AIRCRAFT_OPERATOR)-[ve:VERIFIED_EMISSIONS]->(p:PERIOD) " +
                            "RETURN sum(ve.value) AS Verified_Emissions, p.name AS Period ORDER BY p.name");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double emissions = Double.parseDouble(String.valueOf(row.get( "Verified_Emissions" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setVerifiedEmissionsEUWide(period, emissions, VerifiedEmissionsEUWide.EMISSIONS_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    Result installationsResult = databaseManager.graphDb.execute( "MATCH (i:INSTALLATION)-[ve:VERIFIED_EMISSIONS]->(p:PERIOD) " +
                            "RETURN sum(ve.value) AS Verified_Emissions, p.name AS Period ORDER BY p.name");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double emissions = Double.parseDouble(String.valueOf(row.get( "Verified_Emissions" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setVerifiedEmissionsEUWide(period, emissions, VerifiedEmissionsEUWide.EMISSIONS_TYPE_INSTALLATIONS);
                    }

                    //**********************************************************//
                    //****************** FREE ALLOCATION ********************//
                    System.out.println("Calculating free allocation...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    allResult = databaseManager.graphDb.execute( "MATCH ()-[aa:ALLOWANCES_IN_ALLOCATION]->(p:PERIOD) " +
                            "RETURN sum(aa.value) AS Free_Allocation, p.name AS Period ORDER BY p.name");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double allocation = Double.parseDouble(String.valueOf(row.get( "Free_Allocation" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAllowancesInAllocationEUWide(period, allocation, AllowancesInAllocationEUWide.ALLOWANCES_IN_ALLOCATION_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    aviationResult = databaseManager.graphDb.execute( "MATCH (ao:AIRCRAFT_OPERATOR)-[aa:ALLOWANCES_IN_ALLOCATION]->(p:PERIOD) " +
                            "RETURN sum(aa.value) AS Free_Allocation, p.name AS Period ORDER BY p.name");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double allocation = Double.parseDouble(String.valueOf(row.get( "Free_Allocation" )));
                        String periodSt = String.valueOf(row.get("Period"));

                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAllowancesInAllocationEUWide(period, allocation, AllowancesInAllocationEUWide.ALLOWANCES_IN_ALLOCATION_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    installationsResult = databaseManager.graphDb.execute( "MATCH (i:INSTALLATION)-[aa:ALLOWANCES_IN_ALLOCATION]->(p:PERIOD) " +
                            "RETURN sum(aa.value) AS Free_Allocation, p.name AS Period ORDER BY p.name");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double allocation = Double.parseDouble(String.valueOf(row.get( "Free_Allocation" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAllowancesInAllocationEUWide(period, allocation, AllowancesInAllocationEUWide.ALLOWANCES_IN_ALLOCATION_TYPE_INSTALLATIONS);
                    }

                    //*************************************************************************************//
                    //********************************** LEGAL CAP   ********************************//

                    System.out.println("Calculating legal cap...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    allResult = databaseManager.graphDb.execute( "MATCH (node)-[lc:LEGAL_CAP]->(p:PERIOD) " +
                            "WHERE node:COUNTRY OR (node:SANDBAG_SECTOR AND node.name = 'Aviation') " +
                            "RETURN sum(lc.amount) AS Legal_Cap, p.name AS Period ORDER BY p.name");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double legalCap = Double.parseDouble(String.valueOf(row.get( "Legal_Cap" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setLegalCapEUWide(period, legalCap, LegalCapEUWide.LEGAL_CAP_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    aviationResult = databaseManager.graphDb.execute( "MATCH (node)-[lc:LEGAL_CAP]->(p:PERIOD) " +
                            "WHERE (node:SANDBAG_SECTOR AND node.name = 'Aviation') " +
                            "RETURN sum(lc.amount) AS Legal_Cap, p.name AS Period ORDER BY p.name");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double legalCap = Double.parseDouble(String.valueOf(row.get( "Legal_Cap" )));
                        String periodSt = String.valueOf(row.get("Period"));

                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setLegalCapEUWide(period, legalCap, LegalCapEUWide.LEGAL_CAP_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    installationsResult = databaseManager.graphDb.execute( "MATCH (:COUNTRY)-[lc:LEGAL_CAP]->(p:PERIOD) " +
                            "RETURN lc.amount AS Legal_Cap, p.name AS Period ORDER BY p.name");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double legalCap = Double.parseDouble(String.valueOf(row.get( "Legal_Cap" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setLegalCapEUWide(period, legalCap, LegalCapEUWide.LEGAL_CAP_TYPE_INSTALLATIONS);
                    }

                    //*************************************************************************************//
                    //********************************** OFFSETS   ********************************//

                    System.out.println("Calculating offsets...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    allResult = databaseManager.graphDb.execute( "MATCH (node)-[:OFFSETS]-(o:OFFSET)-[:OFFSET_PERIOD]->(p:PERIOD) " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') AND (node:INSTALLATION OR node:AIRCRAFT_OPERATOR) " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name " +
                            "UNION " +
                            "MATCH (p:PERIOD)<-[:OFFSET_PERIOD]-(o:OFFSET)-[offs:OFFSETS_2013_ONWARDS]-() " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') AND offs.type = 'all' " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double offsets = Double.parseDouble(String.valueOf(row.get( "Offsets" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetsEUWide(period, offsets, OffsetsEUWide.OFFSETS_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    aviationResult = databaseManager.graphDb.execute("MATCH (:AIRCRAFT_OPERATOR)-[:OFFSETS]-(o:OFFSET)-[:OFFSET_PERIOD]->(p:PERIOD) " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name " +
                            "UNION " +
                            "MATCH (p:PERIOD)<-[:OFFSET_PERIOD]-(o:OFFSET)-[offs:OFFSETS_2013_ONWARDS]-() " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') AND offs.type = 'aviation' " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double offsets = Double.parseDouble(String.valueOf(row.get( "Offsets" )));
                        String periodSt = String.valueOf(row.get("Period"));

                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetsEUWide(period, offsets, OffsetsEUWide.OFFSETS_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    installationsResult = databaseManager.graphDb.execute( "MATCH (:INSTALLATION)-[:OFFSETS]-(o:OFFSET)-[:OFFSET_PERIOD]->(p:PERIOD) " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name " +
                            "UNION " +
                            "MATCH (p:PERIOD)<-[:OFFSET_PERIOD]-(o:OFFSET)-[offs:OFFSETS_2013_ONWARDS]-() " +
                            "WHERE (o.unit_type = 'ERU' OR o.unit_type = 'CER') AND offs.type = 'installations' " +
                            "RETURN sum(o.amount) AS Offsets, p.name AS Period ORDER BY p.name");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double offsets = Double.parseDouble(String.valueOf(row.get( "Offsets" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetsEUWide(period, offsets, OffsetsEUWide.OFFSETS_TYPE_INSTALLATIONS);
                    }

                    //*************************************************************************************//
                    //********************************** OFFSET ENTITLEMENTS  ********************************//

                    System.out.println("Calculating offset entitlements...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    allResult = databaseManager.graphDb.execute( "MATCH ()-[r:OFFSET_ENTITLEMENT]->(p:PERIOD) RETURN sum(toFloat(r.value)) AS Total_Entitlements, p.name AS Period");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double offsetEntitlements = Double.parseDouble(String.valueOf(row.get( "Total_Entitlements" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetEntitlementsEUWide(period, offsetEntitlements, OffsetEntitlementEUWide.OFFSET_ENTITLEMENTS_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    aviationResult = databaseManager.graphDb.execute("MATCH (:AIRCRAFT_OPERATOR)-[r:OFFSET_ENTITLEMENT]->(p:PERIOD) RETURN sum(toFloat(r.value)) AS Total_Entitlements, p.name AS Period");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double offsetEntitlements = Double.parseDouble(String.valueOf(row.get( "Total_Entitlements" )));
                        String periodSt = String.valueOf(row.get("Period"));

                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetEntitlementsEUWide(period, offsetEntitlements, OffsetEntitlementEUWide.OFFSET_ENTITLEMENTS_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    installationsResult = databaseManager.graphDb.execute( "MATCH (:INSTALLATION)-[r:OFFSET_ENTITLEMENT]->(p:PERIOD) RETURN sum(toFloat(r.value)) AS Total_Entitlements, p.name AS Period");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double allocation = Double.parseDouble(String.valueOf(row.get( "Total_Entitlements" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setOffsetEntitlementsEUWide(period, allocation, OffsetEntitlementEUWide.OFFSET_ENTITLEMENTS_TYPE_INSTALLATIONS);
                    }

                    //*************************************************************************************//
                    //********************************** AUCTIONED  ********************************//

                    System.out.println("Calculating auctions...");

                    //+++++++++++++++++++++ all +++++++++++++++++++++++++++++++

                    allResult = databaseManager.graphDb.execute( "MATCH (c:COUNTRY)-[a:AUCTIONED]->(p:PERIOD) " +
                            "RETURN sum(a.amount) AS Auctioned, p.name AS Period ORDER BY p.name " +
                            "UNION " +
                            "MATCH (n:NER300)-[a:AUCTIONED]->(p:PERIOD) " +
                            "RETURN sum(a.amount) AS Auctioned, p.name AS Period ORDER BY p.name");

                    while ( allResult.hasNext() )
                    {
                        Map<String,Object> row = allResult.next();
                        double auctioned = Double.parseDouble(String.valueOf(row.get( "Auctioned" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAuctionedEUWide(period, auctioned, AuctionedEUWide.AUCTIONED_TYPE_ALL);
                    }

                    //+++++++++++++++++++++ aviation +++++++++++++++++++++++++++++++

                    aviationResult = databaseManager.graphDb.execute("MATCH (c:COUNTRY)-[a:AUCTIONED]->(p:PERIOD) " +
                            "WHERE a.type = 'Aircraft Operator' RETURN sum(a.amount) AS Auctioned, p.name AS Period ORDER BY p.name");

                    while ( aviationResult.hasNext() )
                    {
                        Map<String,Object> row = aviationResult.next();

                        double auctioned = Double.parseDouble(String.valueOf(row.get( "Auctioned" )));
                        String periodSt = String.valueOf(row.get("Period"));

                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAuctionedEUWide(period, auctioned, AuctionedEUWide.AUCTIONED_TYPE_AVIATION);
                    }

                    //+++++++++++++++++++++ installations +++++++++++++++++++++++++++++++

                    installationsResult = databaseManager.graphDb.execute( "MATCH (c:COUNTRY)-[a:AUCTIONED]->(p:PERIOD) " +
                            "WHERE a.type = 'Installation' RETURN sum(a.amount) AS Auctioned, p.name AS Period ORDER BY p.name " +
                            "UNION " +
                            "MATCH (n:NER300)-[a:AUCTIONED]->(p:PERIOD) " +
                            "RETURN sum(a.amount) AS Auctioned, p.name AS Period ORDER BY p.name");

                    while ( installationsResult.hasNext() )
                    {
                        Map<String,Object> row = installationsResult.next();

                        double auctioned = Double.parseDouble(String.valueOf(row.get( "Auctioned" )));
                        String periodSt = String.valueOf(row.get("Period"));
                        Period period = databaseManager.getPeriodByName(periodSt);

                        euCountry.setAuctionedEUWide(period, auctioned, AuctionedEUWide.AUCTIONED_TYPE_INSTALLATIONS);
                    }


                }else{
                    System.out.println("EU country could not be found... Exiting the program...");
                }

                tx.success();
                tx.close();

                databaseManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
