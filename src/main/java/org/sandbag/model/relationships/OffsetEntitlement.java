package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public class OffsetEntitlement implements OffsetEntitlementModel{

    protected Relationship relationship;

    public OffsetEntitlement(Relationship relationship){
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
        return Double.parseDouble(String.valueOf(relationship.getProperty(OffsetEntitlementModel.value)));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(OffsetEntitlementModel.value, value);
    }

}
