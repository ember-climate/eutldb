package org.sandbag.model.relationships.aircraft_ops;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.relationships.aircraft_ops.interfaces.AircraftOperatorCountryModel;

/**
 * Created by root on 18/03/16.
 */
public class AircraftOperatorCountry implements AircraftOperatorCountryModel {

    protected Relationship relationship;

    public AircraftOperatorCountry(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public AircraftOperator getAircraftOperator(){
        return new AircraftOperator(relationship.getStartNode());
    }

    public Country getCountry(){
        return new Country(relationship.getEndNode());
    }
}
