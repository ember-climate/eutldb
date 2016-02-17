package org.sandbag.model;

import org.neo4j.graphdb.Relationship;

/**
 * Created by root on 15/02/16.
 */
public class InstallationCountry implements InstallationCountryModel{

    protected Relationship relationship;

    public InstallationCountry(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    public Country getCountry(){
        return new Country(relationship.getEndNode());
    }
}
