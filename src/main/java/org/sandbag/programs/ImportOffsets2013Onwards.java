package org.sandbag.programs;

import org.sandbag.util.Executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by root on 20/05/16.
 */
public class ImportOffsets2013Onwards implements Executable{

    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Database folder \n" +
                    "2. Offsets TSV file");
        }else{

            String dbFolder = args[0];
            String offsetsFileSt = args[1];

            try{

                BufferedReader reader = new BufferedReader(new FileReader(new File(offsetsFileSt)));

                String line;

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");

                    String offsetTypeSt = columns[2].trim();
                    String periodSt = columns[3].trim();
                    String amount = columns[4].trim();
                    String referenceSt = columns[5].trim();
                }

                reader.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }
}
