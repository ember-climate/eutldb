package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Sector;

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
