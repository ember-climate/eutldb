package org.sandbag.model.nodes;

import org.neo4j.graphdb.Label;

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

}
