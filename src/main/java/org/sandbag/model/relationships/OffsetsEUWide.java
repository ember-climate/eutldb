package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.relationships.interfaces.OffsetsEUWideModel;

/**
 * Created by root on 27/07/16.
 */
public class OffsetsEUWide implements OffsetsEUWideModel {

    protected Relationship relationship;

    public OffsetsEUWide(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }


    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(OffsetsEUWideModel.value)));
    }

    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(OffsetsEUWideModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(OffsetsEUWideModel.value, value);
    }

    @Override
    public void setType(String type) {
        relationship.setProperty(OffsetsEUWideModel.type, type);
    }
}
