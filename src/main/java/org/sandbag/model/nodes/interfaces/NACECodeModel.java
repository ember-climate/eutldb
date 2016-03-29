package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 03/03/16.
 */
public interface NACECodeModel extends Label {

    String LABEL = "NACE_CODE";
    String id = "id";
    //String name = "name";

    String getId();
    //String getName();

    void setId(String id);
    //void setName(String name);

}