package org.sandbag.model.relationships.aircraft_ops;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.NACECode;
import org.sandbag.model.relationships.aircraft_ops.interfaces.AircraftOperatorNACECodeModel;

/**
 * Created by root on 28/04/16.
 */
public class AircraftOperatorNACECode implements AircraftOperatorNACECodeModel {

    protected Relationship relationship;

    public AircraftOperatorNACECode(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public AircraftOperator getAircraftOperator(){
        return new AircraftOperator(relationship.getStartNode());
    }

    @Override
    public NACECode getNACECode(){
        return new NACECode(relationship.getEndNode());
    }
}
