package org.sandbag.model.relationships.installations;

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

    @Override
    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    @Override
    public Sector getSector(){
        return new Sector(relationship.getEndNode());
    }
}
