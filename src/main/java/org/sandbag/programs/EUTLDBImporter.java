package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.*;
import org.sandbag.model.nodes.*;
import org.sandbag.model.relationships.AllowancesInAllocationModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by root on 14/03/16.
 */
public class EUTLDBImporter {

    private static DatabaseManager dbManager;

    public static void main(String[] args){
        if(args.length != 6){
            System.out.println("The program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Installations folder\n" +
                    "3. Aircraf Operators folder\n" +
                    "4. Compliance Data folder\n" +
                    "5. NER allocation data file\n" +
                    "6. Article 10c data file");
        }else{

            EUTLDBImporter importer = new EUTLDBImporter(args[0]);

            importer.importInstallationsFromFolder(args[1]);
            importer.importComplianceDataFromFolder(args[3]);

            importer.importNERAllocationData(new File(args[4]));
            importer.importArticle10cAllocationData(new File(args[5]));

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

    public void importComplianceDataFromFolder(String folderSt){
        System.out.println("Importing compliance data from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importComplianceDataFile(currentFile);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

        System.out.println("Done! :)");
    }

    public void importAircraftOperatorFile(File file){

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
                    String countryNameSt = columns[0].trim();
                    String accountTypeSt = columns[1].trim();
                    String accountHolderNameSt = columns[2].trim();
                    String companyRegistrationNumberSt = columns[3].trim();
                    String companyStatusSt = columns[4].trim();
                    String companyTypeSt = columns[5].trim();
                    String companyNameSt = columns[6].trim();
                    String companyMainAddressSt = columns[7].trim();
                    String companySecondaryAddressSt = columns[8].trim();
                    String companyPostalCodeSt = columns[9].trim();
                    String companyCitySt = columns[10].trim();
                    String aircraftOperatorIdSt = columns[11].trim();
                    String uniqueCodeUnderComissionioRegulationSt = columns[12].trim();
                    String monitoringPlanIDSt = columns[13].trim();
                    String monitoringPlanFirstYearOfApplicabilitySt = columns[14].trim();
                    String monitoringPlanYearOfExpiry = columns[15].trim();
                    String subsidiaryCompanySt = columns[16].trim();
                    String parentCompanySt = columns[17].trim();
                    String eprtrIdSt = columns[18].trim();
                    String icaoDesignator = columns[19].trim();
                    String aircraftOperatorMainAddressSt = columns[20].trim();
                    String aircraftOperatorSecondaryAddressSt = columns[21].trim();
                    String aircraftOperatorPostalCodeSt = columns[22].trim();
                    String aircraftOperatorCitySt = columns[23].trim();
                    String countryIdSt = columns[24].trim();
                    String latitudeSt = columns[25].trim();
                    String longitudeSt = columns[26].trim();
                    String mainActivitySt = columns[27].trim();

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

                    String aircraftOperatorCompleteIDSt = countryIdSt + aircraftOperatorIdSt;

                    Installation installation = dbManager.createInstallation(installationCompleteIDSt,installationNameSt,
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

    public void importNERAllocationData(File file){
        System.out.println("Importing file " + file.getName());
        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            while((line = reader.readLine()) != null){

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t");

                    String countryIdSt = columns[0];
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2];
                    String nerAllocationSt = columns[3];

                    Period period = dbManager.getPeriodByName(yearSt);
                    if(period == null){
                        System.out.println("Creating period: " + yearSt);
                        period = dbManager.createPeriod(yearSt);
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }
                    Installation installation = dbManager.getInstallationById(installationIdSt);
                    if(installation != null){

                        if(!nerAllocationSt.isEmpty()){
                            try{
                                double tempValue = Double.parseDouble(nerAllocationSt);
                                installation.setAllowancesInAllocationForPeriod(period, tempValue, AllowancesInAllocationModel.NER_TYPE);
                            }catch(Exception e){
                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
                                System.out.println("Allowances in allocation value: " + nerAllocationSt + " is not a number. It won't be stored");
                            }
                        }

                    }else{
                        System.out.println("(NER data) Installation " + installationIdSt + " could not be found...");
                        System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                        System.out.println("countryIdSt = '" + countryIdSt + "'");
                    }

                    if(lineCounter % 100 == 0){
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }

                    lineCounter++;
                }
            }

            tx.success();
            tx.close();
            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importArticle10cAllocationData(File file){
        System.out.println("Importing file " + file.getName());
        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            while((line = reader.readLine()) != null){

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t");

                    String countryIdSt = columns[0];
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2];
                    String article10cAllocationSt = columns[3];

                    Period period = dbManager.getPeriodByName(yearSt);
                    if(period == null){
                        System.out.println("Creating period: " + yearSt);
                        period = dbManager.createPeriod(yearSt);
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }
                    Installation installation = dbManager.getInstallationById(installationIdSt);
                    if(installation != null){

                        if(!article10cAllocationSt.isEmpty()){
                            try{
                                double tempValue = Double.parseDouble(article10cAllocationSt);
                                installation.setAllowancesInAllocationForPeriod(period, tempValue, AllowancesInAllocationModel.ARTICLE_10C_TYPE);
                            }catch(Exception e){
                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
                                System.out.println("Allowances in allocation value: " + article10cAllocationSt + " is not a number. It won't be stored");
                            }
                        }

                    }else{
                        System.out.println("(Article 10c data) Installation " + installationIdSt + " could not be found...");
                        System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                        System.out.println("countryIdSt = '" + countryIdSt + "'");
                    }

                    if(lineCounter % 100 == 0){
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }

                    lineCounter++;
                }
            }

            tx.success();
            tx.close();
            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
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

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t");

                    String countryIdSt = columns[0];
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2];
                    String allowancesSt = columns[3];
                    String verifiedEmissionsSt = columns[4];
                    String unitsSurrenderedSt = columns[5];
                    String complianceCode = columns[6];

                    Period period = dbManager.getPeriodByName(yearSt);
                    if(period == null){
                        System.out.println("Creating period: " + yearSt);
                        period = dbManager.createPeriod(yearSt);
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }
                    Installation installation = dbManager.getInstallationById(installationIdSt);
                    if(installation != null){

                        //+++++++++++++++++++++ SURRENDERED UNITS++++++++++++++++++++++++++++++++
                        if(!unitsSurrenderedSt.isEmpty()){
                            try{
                                double tempValue = Double.parseDouble(unitsSurrenderedSt);
                                installation.setSurrenderedUnitsForPeriod(period, tempValue);
                            }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Units surrendered value: " + unitsSurrenderedSt + " is not a number. It won't be stored");
                            }
                        }
                        //+++++++++++++++++++++ VERIFIED EMISSIONS++++++++++++++++++++++++++++++++
                        if(!verifiedEmissionsSt.isEmpty()){
                            try{
                                double tempValue = Double.parseDouble(verifiedEmissionsSt);
                                installation.setVerifiedEmissionsForPeriod(period, tempValue);
                            }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Verified emissions value: " + verifiedEmissionsSt + " is not a number. It won't be stored");
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
                                installation.setAllowancesInAllocationForPeriod(period, tempValue, AllowancesInAllocationModel.STANDARD_TYPE);
                            }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Allowances in allocation value: " + allowancesSt + " is not a number. It won't be stored");
                            }
                        }



                    }else{
                        System.out.println("Installation " + installationIdSt + " could not be found...");
                        System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                        System.out.println("countryIdSt = '" + countryIdSt + "'");
                    }

                    if(lineCounter % 100 == 0){
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }


                    lineCounter++;
                }


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
                    String countryNameSt = columns[0].trim();
                    String accountTypeSt = columns[1].trim();
                    String accountHolderNameSt = columns[2].trim();
                    String companyRegistrationNumberSt = columns[3].trim();
                    String companyStatusSt = columns[4].trim();
                    String companyTypeSt = columns[5].trim();
                    String companyNameSt = columns[6].trim();
                    String companyMainAddressSt = columns[7].trim();
                    String companySecondaryAddressSt = columns[8].trim();
                    String companyPostalCodeSt = columns[9].trim();
                    String companyCitySt = columns[10].trim();
                    String installationIdSt = columns[11].trim();
                    String installationNameSt = columns[12].trim();
                    String permitIDSt = columns[13].trim();
                    String permitEntryDateSt = columns[14].trim();
                    String permitExpiryRevocationDateSt = columns[15].trim();
                    String subsidiaryCompanySt = columns[16].trim();
                    String parentCompanySt = columns[17].trim();
                    String eprtrIdSt = columns[18].trim();
                    String installationMainAddressSt = columns[19].trim();
                    String installationSecondaryAddressSt = columns[20].trim();
                    String installationPostalCodeSt = columns[21].trim();
                    String installationCitySt = columns[22].trim();
                    String countryIdSt = columns[23].trim();
                    String latituteSt = columns[24].trim();
                    String longitudeSt = columns[25].trim();
                    String mainActivitySt = columns[26].trim();

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

//                    System.out.println("installationIdSt = " + installationIdSt);
                    String installationCompleteIDSt = countryIdSt + installationIdSt;
//                    System.out.println("installationCompleteIDSt = " + installationCompleteIDSt);


                    Installation installation = dbManager.createInstallation(installationCompleteIDSt,installationNameSt,
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

    public void importAllocationDataForPeriod1File(File file){
        System.out.println("Importing file " + file.getName());

        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            Period period2008 = dbManager.getPeriodByName("2008");
            if(period2008 == null){
                period2008 = dbManager.createPeriod("2008");
            }
            Period period2009 = dbManager.getPeriodByName("2009");
            if(period2009 == null){
                period2009 = dbManager.createPeriod("2009");
            }
            Period period2010 = dbManager.getPeriodByName("2010");
            if(period2010 == null){
                period2010 = dbManager.createPeriod("2010");
            }
            Period period2011 = dbManager.getPeriodByName("2011");
            if(period2011 == null){
                period2011 = dbManager.createPeriod("2011");
            }
            Period period2012 = dbManager.getPeriodByName("2012");
            if(period2012 == null){
                period2012 = dbManager.createPeriod("2012");
            }

            while((line = reader.readLine()) != null){

                String[] columns = line.split("\t");
                String countryIdSt = columns[0];
                String installationIdSt = columns[1].trim();
                String latestUpdateSt = columns[2];
                String year2008St = columns[3];
                String year2009St = columns[4];
                String year2010St = columns[5];
                String year2011St = columns[6];
                String year2012St = columns[7];

                String installationCompleteID = countryIdSt + installationIdSt;
                Installation installation = dbManager.getInstallationById(installationCompleteID);
                if(installation != null){

                    if(!year2008St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2008St);
                            installation.setFreeAllocationForPeriod(period2008, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2008: " + year2008St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2009St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2009St);
                            installation.setFreeAllocationForPeriod(period2009, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2009: " + year2009St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2010St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2010St);
                            installation.setFreeAllocationForPeriod(period2010, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2010: " + year2010St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2011St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2011St);
                            installation.setFreeAllocationForPeriod(period2011, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2011: " + year2011St + " is not a number. It won't be stored");
                        }
                    }
                    if(!year2012St.isEmpty()){
                        try{
                            double tempValue = Double.parseDouble(year2012St);
                            installation.setFreeAllocationForPeriod(period2012, tempValue,"");
                        }catch (Exception e){
                            System.out.println("Problem with installation: " + installationCompleteID);
                            System.out.println("Allocation value for period 2010: " + year2012St + " is not a number. It won't be stored");
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

    public void importAllocationDataForPeriod0File(File file){
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
