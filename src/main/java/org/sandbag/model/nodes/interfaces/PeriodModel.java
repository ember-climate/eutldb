package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/02/16.
 */
public interface PeriodModel extends Label {

    String LABEL = "PERIOD";
    String name = "name";

    String getName();
    void setName(String name);

}
