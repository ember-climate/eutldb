package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 15/02/16.
 */
public interface CompanyModel extends Label {

    String LABEL = "PARENT_COMPANY";
    String name = "name";

    String getName();
    Company getParentCompany();

    void setName(String name);
    void setParentCompany(Company company);

}
