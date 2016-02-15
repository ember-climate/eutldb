package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 15/02/16.
 */
public interface ParentCompanyModel extends Label {

    String LABEL = "PARENT_COMPANY";
    String name = "name";

    String getName();

    void setName(String name);

    String name();
}
