package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 18/05/16.
 */
public interface FuelTypeModel extends Label {

    String LABEL = "FUEL_TYPE";
    String name = "name";

    String getName();
    void setName(String name);
}
