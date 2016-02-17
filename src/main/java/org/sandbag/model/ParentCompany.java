package org.sandbag.model;

import org.neo4j.graphdb.Relationship;

/**
 * Created by root on 16/02/16.
 */
public class ParentCompany implements ParentCompanyModel {

    protected Relationship relationship;

    public ParentCompany(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public Company getCompany(){
        return new Company(relationship.getStartNode());
    }

    public Company getParentCompany(){
        return new Company(relationship.getEndNode());
    }
}
