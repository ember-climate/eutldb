package org.sandbag.programs;

import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Installation;
import org.sandbag.util.Executable;
import org.sandbag.util.gson.Geometry;
import org.sandbag.util.gson.OpenCageDataResult;
import org.sandbag.util.gson.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 11/07/16.
 */
public class ImportApproximateCoordinatesForInstallations implements Executable {

    public static final String HEADER = "Installation/Aircraft operator ID\tLatitude\tLongitude";


    @Override
    public void execute(List<String> args) {
        main(args.toArray(new String[0]));
    }

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("This program expects the following parameters: \n" +
                    "1. Database folder\n" +
                    "2. Installations to fix TSV file" +
                    "3. Output TSV file with new coordinates");
        }else{

            String dbFolder = args[0];
            String inputFileSt = args[1];
            String outputFileSt = args[2];

            DatabaseManager manager = new DatabaseManager(dbFolder);
            Transaction tx = manager.beginTransaction();

            try{

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileSt)));

                BufferedReader reader = new BufferedReader(new FileReader(new File(inputFileSt)));
                reader.readLine();//header

                String line;
                Gson gson = new Gson();

                while((line = reader.readLine()) != null){

                    String[] columns = line.split("\t");
                    String installationId = columns[0].trim();

                    Installation installation = manager.getInstallationById(installationId);
                    String querySt = installation.getCity() + "," + installation.getCountry().getName();

                    URIBuilder builder = new URIBuilder("http://api.opencagedata.com/geocode/v1/json");
                    builder.addParameter("q", querySt);
                    builder.addParameter("key", "fe12a67561caa821b7daa8baed0a7c8e");

                    String tempSt = builder.toString();

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
                }

                reader.close();
                writer.close();


            }catch (Exception e){
                e.printStackTrace();
            }

            tx.success();
            tx.close();

            manager.shutdown();

            System.out.println("Done!");
        }
    }
}
