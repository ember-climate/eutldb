package org.sandbag.model;

import org.neo4j.graphdb.Relationship;

/**
 * Created by root on 16/02/16.
 */
public class InstallationCompany implements InstallationCompanyModel {
    protected Relationship relationship;

    public InstallationCompany(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    @Override
    public Company getCompany(){
        return new Company(relationship.getEndNode());
    }
}
