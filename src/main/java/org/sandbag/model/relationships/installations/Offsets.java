package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public class Offsets implements OffsetsModel {

    protected Relationship relationship;

    public Offsets(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    public Period getPeriod(){
        return new Period(relationship.getEndNode());
    }

    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(OffsetsModel.value)));
    }
    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(OffsetsModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(OffsetsModel.value, value);
    }
    @Override
    public void setType(String type) {
        relationship.setProperty(OffsetsModel.type, type);
    }
}
