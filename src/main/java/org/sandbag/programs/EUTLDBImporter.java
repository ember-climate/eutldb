package org.sandbag.programs;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.*;

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


                    Installation installation = dbManager.createInstallation(installationIdSt,installationNameSt,
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




}
