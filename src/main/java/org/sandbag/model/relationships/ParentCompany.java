package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Company;
import org.sandbag.model.relationships.interfaces.ParentCompanyModel;

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

    @Override
    public Company getCompany(){
        return new Company(relationship.getStartNode());
    }

    @Override
    public Company getParentCompany(){
        return new Company(relationship.getEndNode());
    }
}
