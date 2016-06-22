package org.sandbag.programs;

import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.relationships.installations.InstallationCountry;
import org.sandbag.util.Executable;
import org.sandbag.util.gson.Geometry;
import org.sandbag.util.gson.OpenCageDataResult;
import org.sandbag.util.gson.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 07/04/16.
 */
public class GeocodingInfoImporter implements Executable {

    public static String HEADER = "INSTALLATION_ID\tLATITUDE\tLONGITUDE";

    static protected DatabaseManager MANAGER;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder \n" +
                    "2. Country code\n" +
                    "3. Output file");
        } else {

            String dbFolderSt = args[0];
            String countryCode = args[1];
            String outputFileSt = args[2];

            System.out.println("countryCode = " + countryCode);

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileSt)));
                writer.write(HEADER + "\n");

                MANAGER = new DatabaseManager(dbFolderSt);

                Transaction tx = MANAGER.beginTransaction();

                Country country = MANAGER.getCountryById(countryCode);
                String countryName = country.getName();
                Iterator<Relationship> relIterator = country.getInstallationCountry();

                Gson gson = new Gson();

                System.out.println("Looping through installations...");
                int installationCounter = 0;


                while (relIterator.hasNext()) {
                    Installation installation = new InstallationCountry(relIterator.next()).getInstallation();
                    String tempLatitude = installation.getLatitude();
                    String tempLongitude = installation.getLongitude();

                    if (tempLatitude.isEmpty() || tempLongitude.isEmpty() || tempLatitude.equals("0") || tempLongitude.equals("0")) {

                        System.out.println("Installation found to be completed: " + installation.getId());

                        String address = installation.getAddress();
                        String city = installation.getCity();
                        String postalCode = installation.getPostCode();

                        String querySt = "";
                        if (!address.isEmpty()) {
                            querySt += address + ",";
                        }
                        querySt += city + ",";
                        if (!postalCode.isEmpty()) {
                            querySt += postalCode + ",";
                        }
                        querySt += countryName;
                        System.out.println("querySt = " + querySt);


                        URIBuilder builder = new URIBuilder("http://api.opencagedata.com/geocode/v1/json");
                        builder.addParameter("q", querySt);
                        builder.addParameter("key", "fe12a67561caa821b7daa8baed0a7c8e");

                        String tempSt = builder.toString();

                        System.out.println("tempSt = " + tempSt);

                        URL url = new URI(tempSt).toURL();
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-length", "0");
                        connection.setUseCaches(false);
                        connection.setAllowUserInteraction(false);
                        connection.connect();
                        int status = connection.getResponseCode();

                        switch (status) {
                            case 200:
                                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = br.readLine()) != null) {
                                    sb.append(line + "\n");
                                }
                                br.close();
                                OpenCageDataResult result = gson.fromJson(sb.toString(), OpenCageDataResult.class);
                                Result[] results = result.results;
                                if (results.length > 0) {
                                    Geometry geometry = results[0].geometry;
                                    installation.setLatitude(geometry.lat);
                                    installation.setLongitude(geometry.lng);
                                    System.out.println("Latitude/longitude found for installation: " + installation.getId() +
                                            " [" + geometry.lat + "," + geometry.lng + "]");

                                    writer.write(installation.getId() + "\t" + geometry.lat + "\t" + geometry.lng + "\n");
                                }
                                break;
                            default:
                                System.out.println("There was a problem with the request :(");
                                System.out.println(connection.getResponseMessage());

                        }

                        System.out.println("connection.getResponseMessage() = " + connection.getResponseMessage());
                        System.out.println("connection.getResponseCode() = " + connection.getResponseCode());


                        installationCounter++;
                        if (installationCounter % 50 == 0) {
                            System.out.println(installationCounter + " installations analyzed so far...");
                        }
                    }


                }

                writer.close();

                tx.success();
                tx.close();
                MANAGER.shutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }


    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }
}
