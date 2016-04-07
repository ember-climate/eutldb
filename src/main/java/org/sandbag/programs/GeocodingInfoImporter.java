package org.sandbag.programs;

import com.google.gson.Gson;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.sandbag.model.DatabaseManager;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.relationships.installations.InstallationCountry;
import org.sandbag.util.gson.Geometry;
import org.sandbag.util.gson.OpenCageDataResult;
import org.sandbag.util.gson.Result;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by root on 07/04/16.
 */
public class GeocodingInfoImporter {

    static protected DatabaseManager MANAGER;

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("This program expects the following parameters:\n" +
                    "1. Database folder \n" +
                    "2. Country code");
        }else{

            String dbFolderSt = args[0];
            String countryCode = args[1];

            GeocodingInfoImporter importer = new GeocodingInfoImporter(dbFolderSt);

            Transaction tx = MANAGER.beginTransaction();

            Country country = MANAGER.getCountryById(countryCode);
            String countryName = country.getName();
            Iterator<Relationship> relIterator = country.getInstallationCountry();

            Gson gson = new Gson();

            while (relIterator.hasNext()){
                Installation installation = new InstallationCountry(relIterator.next()).getInstallation();
                String address = installation.getAddress();
                String city = installation.getCity();
                String postalCode = installation.getPostCode();

                String querySt = "";
                if(!address.isEmpty()){
                    querySt += address + ",";
                }
                querySt += city + ",";
                if(!postalCode.isEmpty()){
                    querySt += postalCode + ",";
                }
                querySt += countryName;

                try {
                    URL url = new URL("http://api.opencagedata.com/geocode/v1/json?q=" + querySt + "&key=e9a58747bc08d13bf685b52bf9771691");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-length", "0");
                    connection.setUseCaches(false);
                    connection.setAllowUserInteraction(false);
                    connection.connect();
                    int status = connection.getResponseCode();

                    switch (status) {
                        case 200:
                        case 201:
                            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line+"\n");
                            }
                            br.close();
                            OpenCageDataResult result = gson.fromJson(sb.toString(), OpenCageDataResult.class);
                            Result[] results = result.results;
                            if(results.length > 0){
                                Geometry geometry = results[0].geometry;
                                installation.setLatitude(geometry.lat);
                                installation.setLongitude(geometry.lng);
                            }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }

            }


            tx.success();
            tx.close();
        }
    }


    public GeocodingInfoImporter(String dbFolder){
        MANAGER = new DatabaseManager(dbFolder);
    }
}
