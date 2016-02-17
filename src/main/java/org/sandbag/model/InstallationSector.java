package org.sandbag.model;

import org.neo4j.graphdb.Relationship;

/**
 * Created by root on 16/02/16.
 */
public class InstallationSector implements InstallationSectorModel{
    protected Relationship relationship;

    public InstallationSector(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    public Sector getSector(){
        return new Sector(relationship.getEndNode());
    }
}
