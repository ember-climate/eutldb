package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.SandbagSector;
import org.sandbag.model.nodes.Sector;
import org.sandbag.model.relationships.interfaces.AggregatesSectorModel;

/**
 * Created by root on 14/04/16.
 */
public class AggregatesSector implements AggregatesSectorModel {

    protected Relationship relationship;

    public AggregatesSector(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Sector getSector(){
        return new Sector(relationship.getEndNode());
    }

    @Override
    public SandbagSector getSandbagSector(){
        return new SandbagSector(relationship.getStartNode());
    }
}
