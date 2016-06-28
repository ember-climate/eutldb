package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Period;

import java.util.Iterator;

/**
 * Created by root on 15/02/16.
 */
public interface CountryModel extends Label{

    String LABEL = "COUNTRY";

    String id = "id";
    String name = "name";
    String centerLatitude = "center_latitude";
    String centerLongitude = "center_longitude";
    String boundingBoxMaxLatitude = "bounding_box_max_latitude";
    String boundingBoxMinLatitude = "bounding_box_min_latitude";
    String boundingBoxMaxLongitude = "bounding_box_max_longitude";
    String boundingBoxMinLongitude = "bounding_box_min_longitude";

    String getId();
    String getName();
    double getCenterLatitude();
    double getCenterLongitude();
    double getBoundingBoxMaxLatitude();
    double getBoundingBoxMinLatitude();
    double getBoundingBoxMaxLongitude();
    double getBoundingBoxMinLongitude();

    void setId(String id);
    void setName(String name);
    void setCenterLatitude(double value);
    void setCenterLongitude(double value);
    void setBoundingBoxMaxLatitude(double value);
    void setBoundingBoxMaxLongitude(double value);
    void setBoundingBoxMinLatitude(double value);
    void setBoundingBoxMinLongitude(double value);
    void setAuctionedForPeriod(Period period, double value, String source);

    Iterator<Relationship> getInstallationCountry();
    Iterator<Relationship> getAircraftOperatorCountry();

}
