package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * Created by root on 30/03/16.
 */
public class ExportDBToMegaFiles {

    public static final String FILE_1_HEADER = "Type\tOHA National Administrator\tOHA Account Type\t" +
            "OHA Account Holder Name\tOHA Installation ID / OHA Aircraft Operator ID\tOHA Company Registration No\t" +
            "OHA Account Status\tOHA Type\tOHA Name\tOHA Main Address Line\tOHA Secondary Address Line\tOHA Postal Code" +
            "\tOHA City\tOHA Country\tInstallation ID / Aircraft Operator ID\tInstallation Name / Unique Code under Commission Regulation (EC) No 748/2009" +
            "\tPermit ID / Monitoring Plan ID\tPermit Entry Date / Monitoring plan  first year of applicability" +
            "\tPermit Expiry/Revocation Date / Monitoring plan  year of expiry\tSubsidiary Company\tParent Company\t" +
            "E-PRTR identification\tCall Sign (ICAO designator)\tMain Address Line\tSecondary Address Line\tPostal Code" +
            "\tCity\tCountry\tLatitude\tLongitude\tMain Activity\tContact Name\tContact Main Address Line" +
            "\tContact Secondary Address Line\tContact Postal Code\tContact City\tContact Country\t2005_allocations" +
            "\t2005_emissions\t2005_surrendered\t2005_cumulative_surrendered\t2005_cumulative_verified_emissions" +
            "\t2005_compliance_code\t2006_allocations\t2006_emissions\t2006_surrendered\t2006_cumulative_surrendered" +
            "\t2006_cumulative_verified_emissions\t2006_compliance_code\t2007_allocations\t2007_emissions\t2007_surrendered" +
            "\t2007_cumulative_surrendered\t2007_cumulative_verified_emissions\t2007_compliance_code\t2008_allocations" +
            "\t2008_emissions\t2008_surrendered\t2008_cumulative_surrendered\t2008_cumulative_verified_emissions\t2008_compliance_code" +
            "\t2009_allocations\t2009_emissions\t2009_surrendered\t2009_cumulative_surrendered\t2009_cumulative_verified_emissions" +
            "\t2009_compliance_code\t2010_allocations\t2010_emissions\t2010_surrendered\t2010_cumulative_surrendered" +
            "\t2010_cumulative_verified_emissions\t2010_compliance_code\t2011_allocations\t2011_emissions\t2011_surrendered" +
            "\t2011_cumulative_surrendered\t2011_cumulative_verified_emissions\t2011_compliance_code\t2012_allocations" +
            "\t2012_emissions\t2012_surrendered\t2012_cumulative_surrendered\t2012_cumulative_verified_emissions\t2012_compliance_code" +
            "\t2013_allocations\t2013_ten_c\t2013_ner\t2013_emissions\t2013_surrendered\t2013_cumulative_surrendered" +
            "\t2013_cumulative_verified_emissions\t2013_compliance_code\t2014_allocations\t2014_ten_c\t2014_ner\t2014_emissions" +
            "\t2014_surrendered\t2014_cumulative_surrendered\t2014_cumulative_verified_emissions\t2014_compliance_code" +
            "\t2015_allocations\t2015_ten_c\t2015_ner\t2015_emissions\t2015_surrendered\t2015_cumulative_surrendered" +
            "\t2015_cumulative_verified_emissions\t2015_compliance_code\t2016_allocations\t2016_ten_c\t2016_ner\t2016_emissions" +
            "\t2016_surrendered\t2016_cumulative_surrendered\t2016_cumulative_verified_emissions\t2016_compliance_code" +
            "\t2017_allocations\t2017_ten_c\t2017_ner\t2017_emissions\t2017_surrendered\t2017_cumulative_surrendered" +
            "\t2017_cumulative_verified_emissions\t2017_compliance_code\t2018_allocations\t2018_ten_c\t2018_ner\t2018_emissions" +
            "\t2018_surrendered\t2018_cumulative_surrendered\t2018_cumulative_verified_emissions\t2018_compliance_code" +
            "\t2019_allocations\t2019_ten_c\t2019_ner\t2019_emissions\t2019_surrendered\t2019_cumulative_surrendered" +
            "\t2019_cumulative_verified_emissions\t2019_compliance_code\t2020_allocations\t2020_ten_c\t2020_ner" +
            "\t2020_emissions\t2020_surrendered\t2020_cumulative_surrendered\t2020_cumulative_verified_emissions\t2020_compliance_code";

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder \n" +
                    "2. Output file 1 \n" +
                    "3. Output file 2");
        }else{

            String dbFolder = args[0];
            String outputFile1St = args[1];

            try{

                DatabaseManager dbManager = new DatabaseManager(dbFolder);

                BufferedWriter file1Buff = new BufferedWriter(new FileWriter(new File(outputFile1St)));

                Iterator<Node> installationIterator = dbManager.findNodes(DatabaseManager.INSTALLATION_LABEL);

                while(installationIterator.hasNext()){

                    String lineSt = "";
                    String typeSt = "Installation";

                    lineSt += typeSt + "\t";

                    Installation installation = new Installation(installationIterator.next());

                    lineSt += installation.getCountry().getName() + "\t";



                }




                file1Buff.close();
                dbManager.shutdown();

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}


