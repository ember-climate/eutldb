package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.*;
import org.sandbag.model.nodes.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 14/03/16.
 */
public class EUTLDBImporter {

    private static DatabaseManager dbManager;

    public static void main(String[] args){
        if(args.length != 4){
            System.out.println("The program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Installations folder\n" +
                    "3. Aircraf Operators folder\n" +
                    "4. Compliance Data folder");
        }else{

            EUTLDBImporter importer = new EUTLDBImporter(args[0]);

            importer.importInstallationsFromFolder(args[1]);
        }
    }

    public EUTLDBImporter(String dbFolder){
        dbManager = new DatabaseManager(dbFolder);
    }

    public void importInstallationsFromFolder(String folderSt){

        System.out.println("Importing installations from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importInstallationsFile(currentFile);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

        System.out.println("Done! :)");
    }

    public void importAircraftOperatorFile(File file){

    }

    public void importComplianceDataFile(File file){

        System.out.println("Importing file " + file.getName());

        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            while((line = reader.readLine()) != null){

                String[] columns = line.split("\t");
                String countryIdSt = columns[0];
                String installationIdSt = columns[1];
                installationIdSt = countryIdSt + installationIdSt;
                String yearSt = columns[2];
                String allowancesSt = columns[3];
                String verifiedEmissionsSt = columns[4];
                String unitsSurrenderedSt = columns[5];
                String complianceCode = columns[6];

                Period period = dbManager.getPeriodByName(yearSt);
                if(period == null){
                    period = dbManager.createPeriod(yearSt);
                }
                Installation installation = dbManager.getInstallationById(installationIdSt);
                if(installation != null){

                    //+++++++++++++++++++++ SURRENDERED UNITS++++++++++++++++++++++++++++++++
                    if(!unitsSurrenderedSt.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(unitsSurrenderedSt);
                            installation.setSurrenderedUnitsForPeriod(period, tempValue);
                        }catch(Exception e){
                            System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
                            System.out.println("Units surrendered value: " + unitsSurrenderedSt + " is not a number. It won't be stored");
                        }
                    }
                    //+++++++++++++++++++++ VERIFIED EMISSIONS++++++++++++++++++++++++++++++++
                    if(!verifiedEmissionsSt.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(verifiedEmissionsSt);
                            installation.setVerifiedEmissionsForPeriod(period, tempValue);
                        }catch(Exception e){
                            System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
                            System.out.println("Verified emissions value: " + verifiedEmissionsSt + " is not a number. It won't be stored");
                        }
                    }
                    //++++++++++++++++++++++ COMPLIANCE ++++++++++++++++++++++++++++++++++++
                    if(!complianceCode.isEmpty()){
                        installation.setComplianceForPeriod(period, complianceCode);
                    }
                    //++++++++++++++++++++ ALLOWANCES IN ALLOCATION ++++++++++++++++++++++++++
                    if(!allowancesSt.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(allowancesSt);
                            installation.setAllowancesInAllocationForPeriod(period, tempValue);
                        }catch(Exception e){
                            System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
                            System.out.println("Allowances in allocation value: " + allowancesSt + " is not a number. It won't be stored");
                        }
                    }



                }

                if(lineCounter % 100 == 0){
                    tx.success();
                    tx.close();
                    tx = dbManager.beginTransaction();
                }


                lineCounter++;
            }

            tx.success();
            tx.close();



            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importInstallationsFile(File file){

        System.out.println("Importing file " + file.getName());

        String line;
        try{

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            while((line = reader.readLine()) != null){

                Transaction tx = dbManager.beginTransaction();

                String[] columns = line.split("\t");
                //System.out.println("columns.length = " + columns.length);
                if(columns.length > 0){
                    String countryNameSt = columns[0];
                    String accountTypeSt = columns[1];
                    String accountHolderNameSt = columns[2];
                    String companyRegistrationNumberSt = columns[3];
                    String companyStatusSt = columns[4];
                    String companyTypeSt = columns[5];
                    String companyNameSt = columns[6];
                    String companyMainAddressSt = columns[7];
                    String companySecondaryAddressSt = columns[8];
                    String companyPostalCodeSt = columns[9];
                    String companyCitySt = columns[10];
                    String installationIdSt = columns[11];
                    String installationNameSt = columns[12];
                    String permitIDSt = columns[13];
                    String permitEntryDateSt = columns[14];
                    String permitExpiryRevocationDateSt = columns[15];
                    String subsidiaryCompanySt = columns[16];
                    String parentCompanySt = columns[17];
                    String eprtrIdSt = columns[18];
                    String installationMainAddressSt = columns[19];
                    String installationSecondaryAddressSt = columns[20];
                    String installationPostalCodeSt = columns[21];
                    String installationCitySt = columns[22];
                    String countryIdSt = columns[23];
                    String latituteSt = columns[24];
                    String longitudeSt = columns[25];
                    String mainActivitySt = columns[26];

                    Country country = dbManager.getCountryByName(countryNameSt);
                    if(country == null){
                        country = dbManager.createCountry(countryNameSt, countryIdSt);
                    }
                    Company company = dbManager.getCompanyByRegistrationNumber(companyRegistrationNumberSt);
                    if(company == null){
                        if(!companyRegistrationNumberSt.isEmpty()){
                            company = dbManager.createCompany(companyNameSt,companyRegistrationNumberSt,companyPostalCodeSt,
                                    companyCitySt, companyMainAddressSt + "\n" + companySecondaryAddressSt, companyStatusSt);
                        }
                    }

                    String sectorId = mainActivitySt.split("-")[0];
                    String sectorName = mainActivitySt.split("-")[1];

                    Sector sector = dbManager.getSectorById(sectorId);
                    if(sector == null){
                        if(!sectorId.isEmpty()){
                            sector = dbManager.createSector(sectorId, sectorName);
                        }
                    }


                    Installation installation = dbManager.createInstallation(countryIdSt + installationIdSt,installationNameSt,
                            installationCitySt, installationPostalCodeSt, installationMainAddressSt + " " + installationSecondaryAddressSt,
                            eprtrIdSt, permitIDSt, permitEntryDateSt, permitExpiryRevocationDateSt, latituteSt, longitudeSt,
                            country, company, sector);

                    tx.success();
                    tx.close();
                }

            }



            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public void importAllocationDataPeriod0File(File file){
        System.out.println("Importing file " + file.getName());

        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            Period period2005 = dbManager.getPeriodByName("2005");
            if(period2005 == null){
                period2005 = dbManager.createPeriod("2005");
            }
            Period period2006 = dbManager.getPeriodByName("2006");
            if(period2006 == null){
                period2006 = dbManager.createPeriod("2006");
            }
            Period period2007 = dbManager.getPeriodByName("2007");
            if(period2007 == null){
                period2007 = dbManager.createPeriod("2007");
            }

            while((line = reader.readLine()) != null){

                String[] columns = line.split("\t");
                String countryIdSt = columns[0];
                String installationIdSt = columns[1];
                String latestUpdateSt = columns[2];
                String year2005St = columns[3];
                String year2006St = columns[4];
                String year2007St = columns[5];

                String installationCompleteID = countryIdSt + installationIdSt;
                Installation installation = dbManager.getInstallationById(installationCompleteID);
                if(installation != null){
                    if(!year2005St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2005St);
                            installation.setFreeAllocationForPeriod(period2005, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2005: " + year2005St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2006St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2006St);
                            installation.setFreeAllocationForPeriod(period2006, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2006: " + year2006St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2007St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2007St);
                            installation.setFreeAllocationForPeriod(period2007, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2007: " + year2007St + " is not a number. It won't be stored");
                        }
                    }

                }else{
                    System.out.println("Installation not found: " + installationCompleteID);
                }




                if(lineCounter % 100 == 0){
                    tx.success();
                    tx.close();
                    tx = dbManager.beginTransaction();
                }


                lineCounter++;
            }

            tx.success();
            tx.close();



            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
