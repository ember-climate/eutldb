package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.OffsetPeriodModel;

/**
 * Created by root on 29/03/16.
 */
public class OffsetPeriod implements OffsetPeriodModel {

    protected Relationship relationship;

    public OffsetPeriod(Relationship relationship){
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
    public Offset getOffset(){
        return new Offset(relationship.getStartNode());
    }
}
