package org.sandbag.programs;

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

            while((line = reader.readLine()) != null){
                String[] columns = line.split("\t");
                String countrySt = columns[0];
                String companyTypeSt = columns[1];
                String companyNameSt = columns[2];
                String idSt = columns[3];
                String companyRegistrationNumberSt = columns[4];
                String statusSt = columns[5];
                String nameSt = columns[7];
                String
            }



            reader.close();


        }catch (Exception e){
            e.printStackTrace();
        }




    }


}
