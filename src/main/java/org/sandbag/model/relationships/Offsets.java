package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.OffsetsModel;

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
    public AircraftOperator getAircraftOperator(){ return new AircraftOperator(relationship.getStartNode());}


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

    @Override
    public Offset getOffset() {
        return new Offset(relationship.getEndNode());
    }
}
