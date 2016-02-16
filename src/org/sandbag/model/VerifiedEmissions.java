package org.sandbag.model;

import org.neo4j.graphdb.Relationship;

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
    public String getValue() {
        return String.valueOf(relationship.getProperty(VerifiedEmissionsModel.value));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(VerifiedEmissionsModel.value, value);
    }
}
