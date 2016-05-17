package org.sandbag.util;

import org.sandbag.util.ExecuteFile;

/**
 * Created by root on 16/05/16.
 */
public class ExecuteEUTLDBProgram {

    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Executions XML file");
        }else{

            String[] array = new String[1];
            array[0] = args[0];
            ExecuteFile.main(array);
        }
    }
}
