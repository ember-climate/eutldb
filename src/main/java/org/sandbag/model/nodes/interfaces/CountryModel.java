package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;

import java.util.Iterator;

/**
 * Created by root on 15/02/16.
 */
public interface CountryModel extends Label{

    String LABEL = "COUNTRY";

    String id = "id";
    String name = "name";

    String getId();
    String getName();

    void setId(String id);
    void setName(String name);

    Iterator<Relationship> getInstallationCountry();
    Iterator<Relationship> getAircraftOperatorCountry();

}
