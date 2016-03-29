package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.VerifiedEmissionsModel;

/**
 * Created by root on 16/02/16.
 */
public class VerifiedEmissions implements VerifiedEmissionsModel {

    protected Relationship relationship;

    public VerifiedEmissions(Relationship relationship){
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
        return Double.parseDouble(String.valueOf(relationship.getProperty(VerifiedEmissionsModel.value)));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(VerifiedEmissionsModel.value, value);
    }
}
