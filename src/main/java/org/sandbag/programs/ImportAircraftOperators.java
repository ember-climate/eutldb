package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Company;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Sector;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 06/06/16.
 */
public class ImportAircraftOperators implements Executable {

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){

        if (args.length != 2) {
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Aircraft Operators folder");
        }else{

            String dbFolder = args[0];
            String aircraftOperatorsFolder = args[1];
            DatabaseManager databaseManager = new DatabaseManager(dbFolder);
            importAircraftOperatorsFromFolder(aircraftOperatorsFolder, databaseManager);
            databaseManager.shutdown();

        }
    }

    protected static void importAircraftOperatorsFromFolder(String folderSt, DatabaseManager databaseManager){

        System.out.println("Importing aircraft operators from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importAircraftOperatorFile(currentFile, databaseManager);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

        System.out.println("Done! :)");
    }


    protected static void importAircraftOperatorFile(File file, DatabaseManager dbManager){

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
                    Company company = dbManager.getCompanyByName(companyNameSt);
                    if(company == null){
                        if(!companyNameSt.isEmpty()){
                            company = dbManager.createCompany(companyNameSt,companyRegistrationNumberSt,companyPostalCodeSt,
                                    companyCitySt, companyMainAddressSt + "\n" + companySecondaryAddressSt, companyStatusSt,
                                    subsidiaryCompanySt, parentCompanySt);
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
}
