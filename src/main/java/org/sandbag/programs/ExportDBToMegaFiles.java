package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.*;
import org.sandbag.model.relationships.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * Created by root on 30/03/16.
 */
public class ExportDBToMegaFiles {

    public static final String FILE_1_HEADER = "Type\tOHA National Administrator\t" +
            "OHA Installation ID / OHA Aircraft Operator ID\tOHA Company Registration No\t" +
            "OHA Account Status\tOHA Name\tOHA Address\tOHA Postal Code" +
            "\tOHA City\tInstallation Name / Unique Code under Commission Regulation (EC) No 748/2009" +
            "\tPermit ID / Monitoring Plan ID\tPermit Entry Date / Monitoring plan  first year of applicability" +
            "\tPermit Expiry/Revocation Date / Monitoring plan  year of expiry\t" +
            "E-PRTR identification\tCall Sign (ICAO designator)\tAddress\tPostal Code" +
            "\tCity\tLatitude\tLongitude\tMain Activity\t2005_allocations\t2005_emissions\t2005_surrendered\t" +
            "2005_compliance_code\t2006_allocations\t2006_emissions\t2006_surrendered\t" +
            "2006_compliance_code\t2007_allocations\t2007_emissions\t2007_surrendered\t2007_compliance_code\t2008_allocations" +
            "\t2008_emissions\t2008_surrendered\t2008_compliance_code\t2009_allocations\t2009_emissions\t2009_surrendered" +
            "\t2009_compliance_code\t2010_allocations\t2010_emissions\t2010_surrendered\t2010_compliance_code\t" +
            "2011_allocations\t2011_emissions\t2011_surrendered\t2011_compliance_code\t2012_allocations\t2012_emissions" +
            "\t2012_surrendered\t2012_compliance_code\t2013_allocations\t2013_ten_c\t2013_ner\t2013_emissions\t" +
            "2013_surrendered\t2013_compliance_code\t2014_allocations\t2014_ten_c\t2014_ner\t2014_emissions" +
            "\t2014_surrendered\t2014_compliance_code\t2015_allocations\t2015_ten_c\t2015_ner\t2015_emissions\t" +
            "2015_surrendered\t2015_compliance_code\t2016_allocations\t2016_ten_c\t2016_ner\t2016_emissions" +
            "\t2016_surrendered\t2016_compliance_code\t2017_allocations\t2017_ten_c\t2017_ner\t2017_emissions\t" +
            "2017_surrendered\t2017_compliance_code\t2018_allocations\t2018_ten_c\t2018_ner\t2018_emissions" +
            "\t2018_surrendered\t2018_compliance_code\t2019_allocations\t2019_ten_c\t2019_ner\t2019_emissions\t" +
            "2019_surrendered\t2019_compliance_code\t2020_allocations\t2020_ten_c\t2020_ner" +
            "\t2020_emissions\t2020_surrendered\t2020_compliance_code";

    public static final String OFFSETS_FILE_HEADER = "Type\tInstallation / Aircraft Operator Country\tInstallation / Aircraft Operator ID\tOriginating Country\tUnit Type\tAmount\t" +
            "Year of Compliance\tProject Identifier";

    public static final String OFFSET_ENTITLEMENTS_FILE_HEADER = "Type\tInstallation / Aircraft Operator Country\t" +
            "Installation / Aircraft Operator ID\tValue";

    public static void main(String[] args){
        if(args.length != 4){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder \n" +
                    "2. Output file 1 (Mega file) \n" +
                    "3. Output file 2 (Offsets file) \n" +
                    "4. Output file 3 (Offset entitlements)");
        }else{

            String dbFolder = args[0];
            String outputFile1St = args[1];
            String offsetsFileSt = args[2];
            String offsetEntitlementsFileSt = args[3];

            try{

                DatabaseManager dbManager = new DatabaseManager(dbFolder);

                Transaction tx = dbManager.beginTransaction();

                BufferedWriter file1Buff = new BufferedWriter(new FileWriter(new File(outputFile1St)));
                file1Buff.write(FILE_1_HEADER + "\n");

                BufferedWriter offsetsFileBuff = new BufferedWriter(new FileWriter(new File(offsetsFileSt)));
                offsetsFileBuff.write(OFFSETS_FILE_HEADER + "\n");

                BufferedWriter offsetEntitlementsBuff = new BufferedWriter(new FileWriter(new File(offsetEntitlementsFileSt)));
                offsetEntitlementsBuff.write(OFFSET_ENTITLEMENTS_FILE_HEADER + "\n");

                Iterator<Node> installationIterator = dbManager.findNodes(DatabaseManager.INSTALLATION_LABEL);

                int installationsCounter = 0;

                while(installationIterator.hasNext()){

                    String lineSt = "";
                    String typeSt = "Installation";

                    lineSt += typeSt + "\t";

                    Installation installation = new Installation(installationIterator.next());
                    Company company = installation.getCompany();
                    Sector sector = installation.getSector();

                    lineSt += installation.getCountry().getName() + "\t" + installation.getId() + "\t" +
                            company.getRegistrationNumber() + "\t" + company.getStatus() + "\t" +
                            company.getName() + "\t" + company.getAddress().replaceAll("\n", " ") + "\t" +
                            company.getPostalCode() + "\t" + company.getCity() + "\t" + installation.getName() +
                            "\t" + installation.getPermitId() + "\t" + installation.getPermitEntryDate() + "\t" +
                            installation.getPermitExpiryOrRevocationDate() + "\t" + installation.getEprtrId() + "\t\t" +
                            installation.getAddress().replaceAll("\n", " ") + "\t" + installation.getPostCode() + "\t" + installation.getCity() +
                            "\t" + installation.getLatitude() + "\t" + installation.getLongitude() + "\t" +
                            sector.getId() + "-" + sector.getName() + "\t";


                    for (int yearCounter=2005;yearCounter<=2012;yearCounter++){

                        String periodSt = String.valueOf(yearCounter);
                        Period period = dbManager.getPeriodByName(periodSt);

                        AllowancesInAllocation allowancesInAllocation = installation.getAllowancesInAllocationForPeriod(period);
                        if(allowancesInAllocation != null){
                            lineSt += allowancesInAllocation.getValue();
                        }
                        lineSt += "\t";

                        VerifiedEmissions verifiedEmissions = installation.getVerifiedEmissionsForPeriod(period);
                        if(verifiedEmissions != null){
                            lineSt += verifiedEmissions.getValue();
                        }
                        lineSt += "\t";

                        SurrenderedUnits surrenderedUnits = installation.getSurrenderedUnitsForPeriod(period);
                        if(surrenderedUnits != null){
                            lineSt += surrenderedUnits.getValue();
                        }
                        lineSt += "\t";

                        Compliance compliance = installation.getComplianceForPeriod(period);
                        if(compliance != null){
                            lineSt += compliance.getValue();
                        }
                        lineSt += "\t";

                    }

                    for (int yearCounter=2013;yearCounter<=2020;yearCounter++){

                        Period period = dbManager.getPeriodByName(String.valueOf(yearCounter));

                        AllowancesInAllocation standardAllocations = installation.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.STANDARD_TYPE);
                        if(standardAllocations != null){
                            lineSt += standardAllocations.getValue();
                        }
                        lineSt += "\t";

                        AllowancesInAllocation tencAllocations = installation.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.ARTICLE_10C_TYPE);
                        if(tencAllocations != null){
                            lineSt += tencAllocations.getValue();
                        }
                        lineSt += "\t";

                        AllowancesInAllocation nerAllocations = installation.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.NER_TYPE);
                        if(nerAllocations != null){
                            lineSt += nerAllocations.getValue();
                        }
                        lineSt += "\t";

                        VerifiedEmissions verifiedEmissions = installation.getVerifiedEmissionsForPeriod(period);
                        if(verifiedEmissions != null){
                            lineSt += verifiedEmissions.getValue();
                        }
                        lineSt += "\t";

                        SurrenderedUnits surrenderedUnits = installation.getSurrenderedUnitsForPeriod(period);
                        if(surrenderedUnits != null){
                            lineSt += surrenderedUnits.getValue();
                        }
                        lineSt += "\t";

                        Compliance compliance = installation.getComplianceForPeriod(period);
                        if(compliance != null){
                            lineSt += compliance.getValue();
                        }
                        lineSt += "\t";

                    }

                    file1Buff.write(lineSt.substring(0, lineSt.length() - 1) + "\n");

                    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //+++++++++++++++++++++++++ OFFSETS ++++++++++++++++++++++++++++++

                    Iterator<Relationship> offsets = installation.getOffsets();

                    while(offsets.hasNext()){

                        Offsets offsetRel = new Offsets(offsets.next());
                        Offset offset = offsetRel.getOffset();

                        Project project = offset.getProject();
                        String projectIdSt = "";
                        if(project != null){
                            projectIdSt = project.getId();
                        }

                        String originatingCountrySt = offset.getOriginatingCountry().getName();
                        String yearOfComplianceSt = offset.getPeriod().getName();

                        offsetsFileBuff.write("Installation\t" + installation.getCountry().getName() + "\t" +
                                installation.getId() + "\t" + originatingCountrySt + "\t" + offset.getUnitType() + "\t"
                                + offset.getAmount() + "\t" + yearOfComplianceSt + "\t" + projectIdSt + "\n");

                    }

                    //============== 0FFSET ENTITLEMENTS ==================
                    Period tempPeriod = dbManager.getPeriodByName("2008to2020");
                    double offsetEntitlementValue = installation.getOffsetEntitlementForPeriod(tempPeriod);
                    offsetEntitlementsBuff.write("Installation \t" + installation.getCountry().getName() + "\t" +
                            installation.getId() + "\t" + offsetEntitlementValue + "\n");
                    //===================================================

                    installationsCounter++;

                    if(installationsCounter % 100 == 0){
                        System.out.println(installationsCounter + " installations exported");
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }


                }


                tx.success();
                tx.close();

                tx = dbManager.beginTransaction();

                Iterator<Node> aircraftOperatorsIterator = dbManager.findNodes(DatabaseManager.AIRCRAFT_OPERATOR_LABEL);

                int aircraftOperatorsCounter = 0;

                while(aircraftOperatorsIterator.hasNext()){

                    AircraftOperator aircraftOperator = new AircraftOperator(aircraftOperatorsIterator.next());
                    Company company = aircraftOperator.getCompany();
                    Sector sector = aircraftOperator.getSector();

                    String lineSt = "";
                    String typeSt = "Aircraft Operator";

                    lineSt += typeSt + "\t";

                    lineSt += aircraftOperator.getCountry().getName() + "\t" + aircraftOperator.getId() + "\t" +
                            company.getRegistrationNumber() + "\t" + company.getStatus() + "\t" +
                            company.getName() + "\t" + company.getAddress().replaceAll("\n", " ") + "\t" +
                            company.getPostalCode() + "\t" + company.getCity() + "\t"
                            + aircraftOperator.getUniqueCodeUnderCommissionRegulation() + "\t" +
                            aircraftOperator.getMonitoringPlanId() + "\t" +
                            aircraftOperator.getMonitoringPlanFirstYearOfApplicability() + "\t" +
                            aircraftOperator.getMonitoringPlanYearOfExpiry() + "\t" + aircraftOperator.getEprtrId() +
                            "\t" + aircraftOperator.getIcaoDesignator() + "\t" +
                            aircraftOperator.getAddress().replaceAll("\n", " ") + "\t"
                            + aircraftOperator.getPostCode() + "\t" + aircraftOperator.getCity() +
                            "\t" + aircraftOperator.getLatitude() + "\t" + aircraftOperator.getLongitude() + "\t" +
                            sector.getId() + "-" + sector.getName() + "\t";


                    for (int yearCounter=2005;yearCounter<=2012;yearCounter++){

                        String periodSt = String.valueOf(yearCounter);
                        Period period = dbManager.getPeriodByName(periodSt);

                        AllowancesInAllocation allowancesInAllocation = aircraftOperator.getAllowancesInAllocationForPeriod(period);
                        if(allowancesInAllocation != null){
                            lineSt += allowancesInAllocation.getValue();
                        }
                        lineSt += "\t";

                        VerifiedEmissions verifiedEmissions = aircraftOperator.getVerifiedEmissionsForPeriod(period);
                        if(verifiedEmissions != null){
                            lineSt += verifiedEmissions.getValue();
                        }
                        lineSt += "\t";

                        SurrenderedUnits surrenderedUnits = aircraftOperator.getSurrenderedUnitsForPeriod(period);
                        if(surrenderedUnits != null){
                            lineSt += surrenderedUnits.getValue();
                        }
                        lineSt += "\t";

                        Compliance compliance = aircraftOperator.getComplianceForPeriod(period);
                        if(compliance != null){
                            lineSt += compliance.getValue();
                        }
                        lineSt += "\t";

                    }

                    for (int yearCounter=2013;yearCounter<=2020;yearCounter++){

                        Period period = dbManager.getPeriodByName(String.valueOf(yearCounter));

                        AllowancesInAllocation standardAllocations = aircraftOperator.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.STANDARD_TYPE);
                        if(standardAllocations != null){
                            lineSt += standardAllocations.getValue();
                        }
                        lineSt += "\t";

                        AllowancesInAllocation tencAllocations = aircraftOperator.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.ARTICLE_10C_TYPE);
                        if(tencAllocations != null){
                            lineSt += tencAllocations.getValue();
                        }
                        lineSt += "\t";

                        AllowancesInAllocation nerAllocations = aircraftOperator.getAllowancesInAllocationForPeriodAndType(period, AllowancesInAllocation.NER_TYPE);
                        if(nerAllocations != null){
                            lineSt += nerAllocations.getValue();
                        }
                        lineSt += "\t";

                        VerifiedEmissions verifiedEmissions = aircraftOperator.getVerifiedEmissionsForPeriod(period);
                        if(verifiedEmissions != null){
                            lineSt += verifiedEmissions.getValue();
                        }
                        lineSt += "\t";

                        SurrenderedUnits surrenderedUnits = aircraftOperator.getSurrenderedUnitsForPeriod(period);
                        if(surrenderedUnits != null){
                            lineSt += surrenderedUnits.getValue();
                        }
                        lineSt += "\t";

                        Compliance compliance = aircraftOperator.getComplianceForPeriod(period);
                        if(compliance != null){
                            lineSt += compliance.getValue();
                        }
                        lineSt += "\t";

                    }

                    file1Buff.write(lineSt.substring(0, lineSt.length() - 1) + "\n");

                    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //+++++++++++++++++++++++++ OFFSETS ++++++++++++++++++++++++++++++

                    Iterator<Relationship> offsets = aircraftOperator.getOffsets();

                    while(offsets.hasNext()){

                        Offsets offsetRel = new Offsets(offsets.next());
                        Offset offset = offsetRel.getOffset();

                        Project project = offset.getProject();
                        String projectIdSt = "";
                        if(project != null){
                            projectIdSt = project.getId();
                        }

                        String originatingCountrySt = offset.getOriginatingCountry().getName();
                        String yearOfComplianceSt = offset.getPeriod().getName();

                        offsetsFileBuff.write("Aircraft Operator\t" + aircraftOperator.getCountry().getName() + "\t" +
                                aircraftOperator.getId() + "\t" + originatingCountrySt + "\t" + offset.getUnitType() +
                                "\t" + offset.getAmount() + "\t" + yearOfComplianceSt + "\t" + projectIdSt + "\n");

                    }

                    //============== 0FFSET ENTITLEMENTS ==================
                    Period tempPeriod = dbManager.getPeriodByName("2008to2020");
                    double offsetEntitlementValue = aircraftOperator.getOffsetEntitlementForPeriod(tempPeriod);
                    offsetEntitlementsBuff.write("Aircraft Operator\t" + aircraftOperator.getCountry().getName() +
                            "\t" + aircraftOperator.getId() + "\t" + offsetEntitlementValue + "\n");
                    //===================================================

                    aircraftOperatorsCounter++;

                    if(aircraftOperatorsCounter % 100 == 0){
                        System.out.println(aircraftOperatorsCounter + " aircraft operators exported");
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }
                }



                tx.success();
                tx.close();


                file1Buff.close();
                offsetsFileBuff.close();
                offsetEntitlementsBuff.close();
                dbManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}


