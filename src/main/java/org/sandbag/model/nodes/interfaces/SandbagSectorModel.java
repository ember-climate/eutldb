package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 14/04/16.
 */
public interface SandbagSectorModel extends Label {

    String LABEL = "SANDBAG_SECTOR";
    String name = "name";

    String getName();

    void setName(String name);

}
