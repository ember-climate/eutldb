package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public class FreeAllocation implements FreeAllocationModel {

    protected Relationship relationship;

    public FreeAllocation(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public String getType(){   return String.valueOf(relationship.getProperty(FreeAllocationModel.type));}
    @Override
    public double getValue(){   return Double.parseDouble(String.valueOf(relationship.getProperty(FreeAllocationModel.value)));}

    @Override
    public void setValue(double value) {
        relationship.setProperty(FreeAllocationModel.value, value);
    }
    @Override
    public void setType(String value) {
        relationship.setProperty(FreeAllocationModel.type, value);
    }

    @Override
    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }
    @Override
    public Period getPeriod(){
        return new Period(relationship.getEndNode());
    }
}
