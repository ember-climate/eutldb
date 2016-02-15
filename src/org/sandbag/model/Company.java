package org.sandbag.model;

import org.neo4j.graphdb.Node;

/**
 * Created by root on 15/02/16.
 */
public class Company implements CompanyModel {

    Node node = null;

    public Company(Node node){
        node = node;
    }

    protected String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        node.setProperty(CompanyModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
