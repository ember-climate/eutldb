package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

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
