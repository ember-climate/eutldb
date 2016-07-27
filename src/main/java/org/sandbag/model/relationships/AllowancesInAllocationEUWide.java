package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationEUWideModel;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationModel;

/**
 * Created by root on 27/07/16.
 */
public class AllowancesInAllocationEUWide implements AllowancesInAllocationEUWideModel {

    protected Relationship relationship;

    public AllowancesInAllocationEUWide(Relationship relationship){
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
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(AllowancesInAllocationEUWideModel.value)));
    }

    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(AllowancesInAllocationEUWideModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(AllowancesInAllocationEUWideModel.value, value);
    }

    @Override
    public void setType(String value) {
        relationship.setProperty(AllowancesInAllocationEUWideModel.type, value);
    }
}
