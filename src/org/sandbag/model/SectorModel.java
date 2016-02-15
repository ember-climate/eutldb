package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 15/02/16.
 */
public interface SectorModel extends Label {

    String LABEL = "SECTOR";
    String name = "name";

    String getName();

    void setName(String name);

    String name();
}
