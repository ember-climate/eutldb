package org.sandbag.programs;

import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Company;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Sector;
import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by root on 06/06/16.
 */
public class ImportInstallations implements Executable {

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){

        if (args.length != 2) {
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder\n" +
                    "2. Installations folder");
        }else{

            String dbFolder = args[0];
            String installationsFolder = args[1];
            DatabaseManager databaseManager = new DatabaseManager(dbFolder);
            importInstallationsFromFolder(dbFolder, databaseManager);
            databaseManager.shutdown();

        }
    }

    protected static void importInstallationsFromFolder(String folderSt, DatabaseManager databaseManager){

        System.out.println("Importing installations from folder: " + folderSt);

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importInstallationsFile(currentFile, databaseManager);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

        System.out.println("Done! :)");
    }

    protected static void importInstallationsFile(File file, DatabaseManager databaseManager){

        System.out.println("Importing file " + file.getName());

        String line;
        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            reader.readLine(); //skipping header

            int lineCounter = 0;

            Transaction tx = databaseManager.beginTransaction();

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

                    //System.out.println("installationNameSt = " + installationNameSt);

                    Country country = databaseManager.getCountryByName(countryNameSt);
                    if(country == null){
                        System.out.println("Creating country: [" + countryIdSt + "," + countryNameSt + "]" );
                        country = databaseManager.createCountry(countryNameSt, countryIdSt);
                        tx.success();
                        tx.close();
                        tx = databaseManager.beginTransaction();
                    }
                    Company company = databaseManager.getCompanyByName(companyNameSt);
                    if(company == null){
                        if(!companyNameSt.isEmpty()){
                            company = databaseManager.createCompany(companyNameSt,companyRegistrationNumberSt,companyPostalCodeSt,
                                    companyCitySt, companyMainAddressSt + "\n" + companySecondaryAddressSt, companyStatusSt,
                                    subsidiaryCompanySt, parentCompanySt);
                            tx.success();
                            tx.close();
                            tx = databaseManager.beginTransaction();
                        }
                    }


                    String[] sectorSplit = mainActivitySt.split("-");

                    String sectorId = sectorSplit[0];
                    String sectorName = mainActivitySt.split("-")[1];
                    if(sectorSplit.length > 2){
                        sectorName = "";
                        for(int i=1;i<sectorSplit.length - 1;i++){
                            sectorName += sectorSplit[i] + "-";
                        }
                        sectorName +=  sectorSplit[sectorSplit.length - 1];
                    }


                    Sector sector = databaseManager.getSectorById(sectorId);
                    if(sector == null){
                        if(!sectorId.isEmpty()){
                            System.out.println("Creating sector: " + sectorName);
                            sector = databaseManager.createSector(sectorId, sectorName);
                            tx.success();
                            tx.close();
                            tx = databaseManager.beginTransaction();
                        }
                    }

//                    System.out.println("installationIdSt = " + installationIdSt);
                    String installationCompleteIDSt = countryIdSt + installationIdSt;
//                    System.out.println("installationCompleteIDSt = " + installationCompleteIDSt);


                    Installation installation = databaseManager.createInstallation(installationCompleteIDSt,installationNameSt,
                            installationCitySt, installationPostalCodeSt, installationMainAddressSt + " " + installationSecondaryAddressSt,
                            eprtrIdSt, permitIDSt, permitEntryDateSt, permitExpiryRevocationDateSt, latituteSt, longitudeSt,
                            country, company, sector);


                }

                lineCounter++;

                if(lineCounter % 1000 == 0){
                    tx.success();
                    tx.close();
                    tx = databaseManager.beginTransaction();
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
}
