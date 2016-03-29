package org.sandbag.model.relationships.aircraft_ops;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Sector;

/**
 * Created by root on 18/03/16.
 */
public class AircraftOperatorSector implements AircraftOperatorSectorModel{
    protected Relationship relationship;

    public AircraftOperatorSector(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public AircraftOperator getAircraftOperator(){
        return new AircraftOperator(relationship.getStartNode());
    }

    public Sector getSector(){
        return new Sector(relationship.getEndNode());
    }
}
