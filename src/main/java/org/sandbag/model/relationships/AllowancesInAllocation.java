package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.AllowancesInAllocationModel;

/**
 * Created by root on 16/03/16.
 */
public class AllowancesInAllocation implements AllowancesInAllocationModel {

    protected Relationship relationship;

    public AllowancesInAllocation(Relationship relationship){
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
    public Period getPeriod(){
        return new Period(relationship.getEndNode());
    }

    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(AllowancesInAllocationModel.value)));
    }

    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(AllowancesInAllocationModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(AllowancesInAllocationModel.value, value);
    }

    @Override
    public void setType(String value) {
        relationship.setProperty(AllowancesInAllocationModel.type, value);
    }
}
