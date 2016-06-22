package org.sandbag.programs;

import org.sandbag.util.Executable;

import java.io.*;
import java.util.HashSet;
import java.util.List;

/**
 * Created by root on 21/06/16.
 */
public class FindNewInstallationsInMegaFiles implements Executable{
    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Old General Mega file\n" +
                    "2. New General Mega files\n" +
                    "3. Output file including new installations/aircraft operators");
        }else{

            try{

                String oldMegaFileSt = args[0];
                String newMegaFileSt = args[1];
                String outputFileSt = args[2];

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileSt)));

                BufferedReader reader = new BufferedReader(new FileReader(new File(oldMegaFileSt)));
                String headerSt = reader.readLine();//header
                writer.write(headerSt + "\n");

                String line;

                HashSet<String> oldMegaFileIDs = new HashSet<>();

                System.out.println("Reading old Mega file...");

                while((line = reader.readLine()) != null){
                    String[] columns = line.split("\t");
                    String idSt = columns[2].trim();
                    oldMegaFileIDs.add(idSt);
                }
                reader.close();

                System.out.println("Done!");

                reader = new BufferedReader(new FileReader(new File(newMegaFileSt)));
                reader.readLine();//header

                System.out.println("Reading new Mega file...");

                while((line = reader.readLine()) != null){
                    String[] columns = line.split("\t");
                    String idSt = columns[2].trim();
                    if(!oldMegaFileIDs.contains(idSt)){
                        System.out.println("New installation/aircraft operator found: " + idSt);
                        writer.write(line + "\n");
                    }
                }
                reader.close();
                System.out.println("Done!");

                System.out.println("Closing output file...");
                writer.close();

                System.out.println("Done! :)");

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }
}
