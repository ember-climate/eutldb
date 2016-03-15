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

    public EUTLDBImporter(String dbFolder){
        dbManager = new DatabaseManager(dbFolder);
    }

    public void importInstallationsFromFolder(String folderSt, String dbFolder){

        File folder = new File(folderSt);
        if(folder.isDirectory()){

            for(File currentFile : folder.listFiles()){
                if(currentFile.getName().split("\\.")[1].toLowerCase().equals("csv")){
                    importInstallationsFile(currentFile, dbFolder);
                }
            }

        }else{
            System.out.println("Please enter a valid folder name");
        }

    }

    public void importInstallationsFile(File file, String dbFolder){

        System.out.println("Importing file " + file.getName());

        String line;
        try{

            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skipping header



            Transaction tx = dbManager.beginTransaction();


            while((line = reader.readLine()) != null){

                String[] columns = line.split("\t");
                String countryNameSt = columns[0];
                String accountTypeSt = columns[1];
                String accountHolderNameSt = columns[2];
                String companyRegistrationNumberSt = columns[3];
                String companyStatusSt = columns[4];
                String companyTypeSt = columns[7];
                String companyNameSt = columns[8];
                String companyMainAddressSt = columns[9];
                String companySecondaryAddressSt = columns[10];
                String companyPostalCodeSt = columns[11];
                String companyCitySt = columns[12];
                String installationIdSt = columns[13];
                String installationNameSt = columns[14];
                String permitIDSt = columns[15];
                String permitEntryDateSt = columns[16];
                String permitExpiryRevocationDateSt = columns[17];
                String subsidiaryCompanySt = columns[18];
                String parentCompanySt = columns[19];
                String eprtrIdSt = columns[20];
                String installationMainAddressSt = columns[21];
                String installationSecondaryAddressSt = columns[22];
                String installationPostalCodeSt = columns[23];
                String installationCitySt = columns[24];
                String countryIdSt = columns[25];
                String latituteSt = columns[26];
                String longitudeSt = columns[27];
                String mainActivitySt = columns[28];

                Country country = dbManager.getCountryByName(countryNameSt);
                if(country == null){
                    country = dbManager.createCountry(countryNameSt, countryIdSt);
                }
                Company company = dbManager.getCompanyByRegistrationNumber(companyRegistrationNumberSt);
                if(company == null){
                    if(!companyRegistrationNumberSt.isEmpty()){
                        company = dbManager.createCompany()
                    }
                }
            }



            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }




    }




}
