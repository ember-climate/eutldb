package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.OffsetEntitlementModel;
import org.sandbag.model.relationships.interfaces.OffsetEntitlementsEUWideModel;

/**
 * Created by root on 27/07/16.
 */
public class OffsetEntitlementEUWide implements OffsetEntitlementsEUWideModel {

    protected Relationship relationship;

    public OffsetEntitlementEUWide(Relationship relationship){
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
        return Double.parseDouble(String.valueOf(relationship.getProperty(OffsetEntitlementsEUWideModel.value)));
    }
    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(OffsetEntitlementsEUWideModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(OffsetEntitlementsEUWideModel.value, value);
    }
    @Override
    public void setType(String type) {
        relationship.setProperty(OffsetEntitlementsEUWideModel.type, type);
    }

}

