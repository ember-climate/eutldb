package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.*;
import org.sandbag.model.nodes.*;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * Class to import EUTL database
 * @author Pablo Pareja Tobes
 *
 */
public class EUTLDBImporter {

    private static DatabaseManager dbManager;

    public static void main(String[] args){
        if(args.length != 9){
            System.out.println("The program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Installations folder\n" +
                    "3. Aircraf Operators folder\n" +
                    "4. Compliance Data folder\n" +
                    "5. NER allocation data file\n" +
                    "6. Article 10c data file\n" +
                    "7. Installations Offset Entitlements file\n" +
                    "8. Aircraft Operators Offset Entitlements file\n" +
                    "9. Offsets folder");
        }else{

            EUTLDBImporter importer = new EUTLDBImporter(args[0]);

            importer.importInstallationsFromFolder(args[1]);
            importer.importAircraftOperatorsFromFolder(args[2]);
            importer.importComplianceDataFromFolder(args[3]);

            importer.importNERAllocationData(new File(args[4]));
            importer.importArticle10cAllocationData(new File(args[5]));
            importer.importInstallationsOffsetEntitlements(new File(args[6]));
            importer.importAircraftOperatorsOffsetEntitlements(new File(args[7]));
            importer.importOffsetsFromFolder(args[8]);

            dbManager.shutdown();


        }
    }

    public EUTLDBImporter(String dbFolder){
        dbManager = new DatabaseManager(dbFolder);
    }

    public void importOffsetsFromFolder(String folderSt){
        System.out.println("Importing offsets from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importOffsetsFile(currentFile);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

        System.out.println("Done! :)");
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

    public void importAircraftOperatorsFromFolder(String folderSt){

        System.out.println("Importing aircraft operators from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importAircraftOperatorFile(currentFile);
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

                String[] columns = line.split("\t", -1);
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
                        System.out.println("Creating country: [" + countryIdSt + "," + countryNameSt + "]" );
                        country = dbManager.createCountry(countryNameSt, countryIdSt);
                    }
                    Company company = dbManager.getCompanyByRegistrationNumber(companyRegistrationNumberSt);
                    if(company == null){
                        if(!companyRegistrationNumberSt.isEmpty() || !companyNameSt.isEmpty()){
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

                    AircraftOperator aircraftOperator = dbManager.createAircraftOperator(aircraftOperatorCompleteIDSt,companyNameSt,
                            aircraftOperatorCitySt,aircraftOperatorPostalCodeSt,aircraftOperatorMainAddressSt + aircraftOperatorSecondaryAddressSt,
                            eprtrIdSt, companyStatusSt, uniqueCodeUnderComissionioRegulationSt, monitoringPlanIDSt,
                            monitoringPlanFirstYearOfApplicabilitySt,monitoringPlanYearOfExpiry, icaoDesignator,
                            latitudeSt, longitudeSt, country, company, sector);

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

                    String[] columns = line.split("\t", -1);

                    String countryIdSt = columns[0].trim();
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2].trim();
                    String nerAllocationSt = columns[3].trim();

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

                    String[] columns = line.split("\t", -1);

                    String countryIdSt = columns[0].trim();
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2].trim();
                    String article10cAllocationSt = columns[3].trim();

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

    public void importInstallationsOffsetEntitlements(File file){
        System.out.println("Importing file " + file.getName());
        try{

            String line;
            int lineCounter = 1;
            String offsetEntitlementsPeriod = "2008to2020";

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            Period period = dbManager.getPeriodByName(offsetEntitlementsPeriod);
            if(period == null){
                System.out.println("Creating period: " + offsetEntitlementsPeriod);
                period = dbManager.createPeriod(offsetEntitlementsPeriod);
                tx.success();
                tx.close();
                tx = dbManager.beginTransaction();
            }


            while((line = reader.readLine()) != null){

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t", -1);

                    String countryNameSt = columns[0].trim();
                    String installationIdIncompleteSt = columns[1].trim();

                    String valueSt = columns[2].trim();
                    try{
                        Double value = Double.parseDouble(valueSt);

                        Country country = dbManager.getCountryByName(countryNameSt);
                        String countryIdSt = country.getId();
                        String installationIdSt = countryIdSt + installationIdIncompleteSt;

                        Installation installation = dbManager.getInstallationById(installationIdSt);

                        if(installation != null){

                            installation.setOffsetEntitlementForPeriod(period, value);

                        }else{
                            System.out.println("(Offset entitlement) Installation " + installationIdSt + " could not be found...");
                            System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                            System.out.println("countryIdSt = '" + countryIdSt + "'");
                        }
                    }catch(Exception e){

                    }

                    if(lineCounter % 100 == 0){
                        System.out.println(lineCounter + " installation offset entitlements added...");
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

    public void importAircraftOperatorsOffsetEntitlements(File file){
        System.out.println("Importing file " + file.getName());
        try{

            String line;
            int lineCounter = 1;
            String offsetEntitlementsPeriod = "2008to2020";

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            Period period = dbManager.getPeriodByName(offsetEntitlementsPeriod);
            if(period == null){
                System.out.println("Creating period: " + offsetEntitlementsPeriod);
                period = dbManager.createPeriod(offsetEntitlementsPeriod);
                tx.success();
                tx.close();
                tx = dbManager.beginTransaction();
            }

            while((line = reader.readLine()) != null){

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t", -1);

                    String countryNameSt = columns[0].trim();
                    String aircraftOperatorIncompleteIdSt = columns[1].trim();

                    String valueSt = columns[2].trim();
                    try{
                        Double value = Double.parseDouble(valueSt);

                        Country country = dbManager.getCountryByName(countryNameSt);
                        String countryIdSt = country.getId();
                        String aircraftOperatorIdSt = countryIdSt + aircraftOperatorIncompleteIdSt;

                        AircraftOperator aircraftOperator = dbManager.getAircraftOperatorById(aircraftOperatorIdSt);
                        if(aircraftOperator != null){

                            aircraftOperator.setOffsetEntitlementForPeriod(period, value);

                        }else{
                            System.out.println("(Offset entitlement) Aircraft Operator " + aircraftOperatorIdSt + " could not be found...");
                            System.out.println("aircraftOperatorIncompleteIdSt = '" + aircraftOperatorIncompleteIdSt + "'");
                            System.out.println("countryIdSt = '" + countryIdSt + "'");
                        }
                    }catch(Exception e){

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

                    String[] columns = line.split("\t", -1);

                    String countryIdSt = columns[0].trim();
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String yearSt = columns[2].trim();
                    String allowancesSt = columns[3].trim();
                    String verifiedEmissionsSt = columns[4].trim();
                    String unitsSurrenderedSt = columns[5].trim();
                    String complianceCode = columns[6].trim();

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

                        AircraftOperator aircraftOperator = dbManager.getAircraftOperatorById(installationIdSt);

                        if(aircraftOperator != null){

                            //+++++++++++++++++++++ SURRENDERED UNITS++++++++++++++++++++++++++++++++
                            if(!unitsSurrenderedSt.isEmpty()){
                                try{
                                    double tempValue = Double.parseDouble(unitsSurrenderedSt);
                                    aircraftOperator.setSurrenderedUnitsForPeriod(period, tempValue);
                                }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Units surrendered value: " + unitsSurrenderedSt + " is not a number. It won't be stored");
                                }
                            }
                            //+++++++++++++++++++++ VERIFIED EMISSIONS++++++++++++++++++++++++++++++++
                            if(!verifiedEmissionsSt.isEmpty()){
                                try{
                                    double tempValue = Double.parseDouble(verifiedEmissionsSt);
                                    aircraftOperator.setVerifiedEmissionsForPeriod(period, tempValue);
                                }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Verified emissions value: " + verifiedEmissionsSt + " is not a number. It won't be stored");
                                }
                            }
                            //++++++++++++++++++++++ COMPLIANCE ++++++++++++++++++++++++++++++++++++
                            if(!complianceCode.isEmpty()){
                                aircraftOperator.setComplianceForPeriod(period, complianceCode);
                            }
                            //++++++++++++++++++++ ALLOWANCES IN ALLOCATION ++++++++++++++++++++++++++
                            if(!allowancesSt.isEmpty()){
                                try{
                                    double tempValue = Double.parseDouble(allowancesSt);
                                    aircraftOperator.setAllowancesInAllocationForPeriod(period, tempValue, AllowancesInAllocationModel.STANDARD_TYPE);
                                }catch(Exception e){
//                                System.out.println("Problem with installation: " + installationIdSt + " [" + countryIdSt + "]");
//                                System.out.println("Allowances in allocation value: " + allowancesSt + " is not a number. It won't be stored");
                                }
                            }

                        }else{
                            System.out.println("Installation/aircraft op. " + installationIdSt + " could not be found...");
                            System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                            System.out.println("countryIdSt = '" + countryIdSt + "'");
                        }

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

            int lineCounter = 0;

            Transaction tx = dbManager.beginTransaction();

            while((line = reader.readLine()) != null){

                String[] columns = line.split("\t", -1);
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
                        System.out.println("Creating country: [" + countryIdSt + "," + countryNameSt + "]" );
                        country = dbManager.createCountry(countryNameSt, countryIdSt);
                        tx.success();
                        tx.close();
                        tx = dbManager.beginTransaction();
                    }
                    Company company = dbManager.getCompanyByRegistrationNumber(companyRegistrationNumberSt);
                    if(company == null){
                        if(!companyRegistrationNumberSt.isEmpty() || !companyNameSt.isEmpty()){
                            company = dbManager.createCompany(companyNameSt,companyRegistrationNumberSt,companyPostalCodeSt,
                                    companyCitySt, companyMainAddressSt + "\n" + companySecondaryAddressSt, companyStatusSt);
                            tx.success();
                            tx.close();
                            tx = dbManager.beginTransaction();
                        }
                    }

                    String sectorId = mainActivitySt.split("-")[0];
                    String sectorName = mainActivitySt.split("-")[1];

                    Sector sector = dbManager.getSectorById(sectorId);
                    if(sector == null){
                        if(!sectorId.isEmpty()){
                            System.out.println("Creating sector: " + sectorName);
                            sector = dbManager.createSector(sectorId, sectorName);
                            tx.success();
                            tx.close();
                            tx = dbManager.beginTransaction();
                        }
                    }

//                    System.out.println("installationIdSt = " + installationIdSt);
                    String installationCompleteIDSt = countryIdSt + installationIdSt;
//                    System.out.println("installationCompleteIDSt = " + installationCompleteIDSt);


                    Installation installation = dbManager.createInstallation(installationCompleteIDSt,installationNameSt,
                            installationCitySt, installationPostalCodeSt, installationMainAddressSt + " " + installationSecondaryAddressSt,
                            eprtrIdSt, permitIDSt, permitEntryDateSt, permitExpiryRevocationDateSt, latituteSt, longitudeSt,
                            country, company, sector);


                }

                lineCounter++;

                if(lineCounter % 1000 == 0){
                    tx.success();
                    tx.close();
                    tx = dbManager.beginTransaction();
                    System.out.println(lineCounter + " lines imported...");
                }

            }

            tx.success();
            tx.close();

            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importOffsetsFile(File file){
        System.out.println("Importing file " + file.getName());

        try{

            String line;
            int lineCounter = 1;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header

            Transaction tx = dbManager.beginTransaction();

            while((line = reader.readLine()) != null){

                if(!line.trim().isEmpty()){

                    String[] columns = line.split("\t", -1);

                    if(columns.length < 12){
                        System.out.println("columns.length = " + columns.length);
                        System.out.println("line = " + line);
                    }

                    String countryIdSt = columns[0].trim();
                    String installationIdIncompleteSt = columns[1].trim();
                    String installationIdSt = countryIdSt + installationIdIncompleteSt;
                    String originatingRegistrySt = columns[2].trim();
                    String unitTypeSt = columns[3].trim();
                    String amountSt = columns[4].trim();
                    String originalCommitmentPeriod = columns[5].trim();
                    String applicableCommitmentPeriod = columns[6].trim();
                    String yearOfComplianceSt = columns[7].trim();
                    String lulucfActivitySt = columns[8].trim();
                    String projectIdSt = columns[9].trim();
                    String trackSt = columns[10].trim();
                    String expiryDateSt = columns[11].trim();

                    if(!yearOfComplianceSt.isEmpty() && !originatingRegistrySt.isEmpty() &&
                            !installationIdIncompleteSt.isEmpty()){

                        Period period = dbManager.getPeriodByName(yearOfComplianceSt);
                        if(period == null){
                            System.out.println("Creating period: " + yearOfComplianceSt);
                            period = dbManager.createPeriod(yearOfComplianceSt);
                            tx.success();
                            tx.close();
                            tx = dbManager.beginTransaction();
                        }
                        Country originatingCountry = dbManager.getCountryByName(originatingRegistrySt);
                        if(originatingCountry == null){
                            System.out.println("Creating country: " + originatingRegistrySt);
                            originatingCountry = dbManager.createCountry(originatingRegistrySt,"");
                            tx.success();
                            tx.close();
                            tx = dbManager.beginTransaction();
                        }
                        Project project = null;
                        if(!projectIdSt.isEmpty()){
                            project = dbManager.getProjectById(projectIdSt);
                            if(project == null){
                                System.out.println("Creating project: " + projectIdSt);
                                project = dbManager.createProject(projectIdSt);
                                tx.success();
                                tx.close();
                                tx = dbManager.beginTransaction();
                            }
                        }

                        Installation installation = dbManager.getInstallationById(installationIdSt);
                        if(installation != null){

                            Offset offset = dbManager.createOffset(
                                    amountSt,
                                    unitTypeSt,
                                    installation,
                                    project,
                                    period,
                                    originatingCountry);


                        }else{

                            AircraftOperator aircraftOperator = dbManager.getAircraftOperatorById(installationIdSt);

                            if(aircraftOperator != null){

                                Offset offset = dbManager.createOffset(
                                        amountSt,
                                        unitTypeSt,
                                        aircraftOperator,
                                        project,
                                        period,
                                        originatingCountry);

                            }else{
                                System.out.println("Installation/aircraft op. " + installationIdSt + " could not be found...");
                                System.out.println("installationIdIncompleteSt = '" + installationIdIncompleteSt + "'");
                                System.out.println("countryIdSt = '" + countryIdSt + "'");
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


            }

            tx.success();
            tx.close();


            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
