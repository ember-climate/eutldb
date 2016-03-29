package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;

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
