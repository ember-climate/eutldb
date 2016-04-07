package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 03/03/16.
 */
public interface NACECodeModel extends Label {

    String LABEL = "NACE_CODE";
    String id = "id";
    String description= "description";

    String getId();
    String getDescription();

    void setId(String id);
    void setDescription(String name);

}