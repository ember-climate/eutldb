package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.Offsets2013OnwardsModel;

/**
 * Created by root on 20/05/16.
 */
public class Offsets2013Onwards implements Offsets2013OnwardsModel{

    protected Relationship relationship;

    public Offsets2013Onwards(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Period getPeriod(){
        return new Period(relationship.getEndNode());
    }

    @Override
    public Country getCountry(){
        return new Country(relationship.getStartNode());
    }
}
