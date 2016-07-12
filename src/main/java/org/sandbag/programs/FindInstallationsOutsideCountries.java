package org.sandbag.programs;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 11/07/16.
 */
public class FindInstallationsOutsideCountries implements Executable {

    public static final String HEADER = "Installation/Aircraft operator ID\tLatitude\tLongitude\tAddress\tCity";

    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Database folder\n" +
                    "2. Output TSV file");
        }else{

            try{

                String dbFolder = args[0];
                String outFileSt = args[1];

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileSt)));
                writer.write(HEADER + "\n");
                DatabaseManager manager = new DatabaseManager(dbFolder);

                Transaction tx = manager.beginTransaction();

                Iterator<Node> countryIterator = manager.findNodes(manager.COUNTRY_LABEL);

                System.out.println("Looping through countries...");

                while (countryIterator.hasNext()){
                    Country country = new Country(countryIterator.next());

                    System.out.println("Country: " + country.getName());

                    System.out.println("Looping through installations...");

                    Iterator<Relationship> instCountryIterator = country.getInstallationCountry();

                    while(instCountryIterator.hasNext()){

                        Installation installation = new Installation(instCountryIterator.next().getStartNode());

                        boolean installationOk = false;

                        try{
                            double latitude = Double.parseDouble(installation.getLatitude());
                            double longitude = Double.parseDouble(installation.getLongitude());

                            if(latitude >= country.getBoundingBoxMinLatitude() &&
                                    latitude <= country.getBoundingBoxMaxLatitude() &&
                                    longitude >= country.getBoundingBoxMinLongitude() &&
                                    longitude <= country.getBoundingBoxMaxLongitude()){

                                installationOk = true;

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("Non numeric value for lat/lg in " + installation.getId());
                        }

                        if(!installationOk){
                            writer.write(installation.getId() + "\t" + installation.getLatitude() + "\t" +
                                        installation.getLongitude() + "\t" + installation.getAddress() + "\t" +
                                        installation.getCity() + "\n");
                        }

                    }

                    System.out.println("Looping through aircraft operators...");

                    Iterator<Relationship> aoCountryIterator = country.getAircraftOperatorCountry();

                    while(aoCountryIterator.hasNext()){

                        AircraftOperator aircraftOperator = new AircraftOperator(aoCountryIterator.next().getStartNode());

                        boolean aoOk = false;

                        try{
                            double latitude = Double.parseDouble(aircraftOperator.getLatitude());
                            double longitude = Double.parseDouble(aircraftOperator.getLongitude());

                            if(latitude >= country.getBoundingBoxMinLatitude() &&
                                    latitude <= country.getBoundingBoxMaxLatitude() &&
                                    longitude >= country.getBoundingBoxMinLongitude() &&
                                    longitude <= country.getBoundingBoxMaxLongitude()){

                                aoOk = true;

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("Non numeric value for lat/lg in " + aircraftOperator.getId());
                        }

                        if(!aoOk){
                            writer.write(aircraftOperator.getId() + "\t" + aircraftOperator.getLatitude() + "\t" +
                                    aircraftOperator.getLongitude() + "\t" + aircraftOperator.getAddress() + "\t" +
                                    aircraftOperator.getCity() + "\n");
                        }

                    }
                }

                tx.success();
                tx.close();

                manager.shutdown();
                writer.close();

                System.out.println("Done!");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
