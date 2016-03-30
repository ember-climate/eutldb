package org.sandbag.programs;

/**
 * Created by root on 30/03/16.
 */
public class ExportDBToMegaFiles {

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder \n" +
                    "2. Output file 1 \n" +
                    "3. Output file 2");
        }else{

            String dbFolder = args[0];
            String outputFile1St = args[1];

        }
    }
}


