package org.sandbag.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;

/**
 * Created by root on 15/02/16.
 */
public class Company implements CompanyModel {

    Node node = null;

    public Company(Node node){
        this.node = node;
    }

    protected String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Company getParentCompany(){
        return new Company(node.getSingleRelationship(new ParentCompany(null), Direction.OUTGOING).getEndNode());

    }

    @Override
    public void setName(String name) {
        node.setProperty(CompanyModel.name, name);
    }

    @Override
    public void setParentCompany(Company company){
        node.createRelationshipTo(company.node, new ParentCompany(null));
    }

    @Override
    public String name() {
        return LABEL;
    }
}
