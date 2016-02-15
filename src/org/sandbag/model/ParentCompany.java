package org.sandbag.model;

import org.neo4j.graphdb.Node;

/**
 * Created by root on 15/02/16.
 */
public class ParentCompany implements ParentCompanyModel{

    Node node = null;

    public ParentCompany(Node node){
        node = node;
    }

    protected String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        node.setProperty(ParentCompanyModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
