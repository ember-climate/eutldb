package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/03/16.
 */
public class Compliance implements ComplianceModel {

    protected Relationship relationship;

    public Compliance(Relationship relationship){
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
        return String.valueOf(relationship.getProperty(ComplianceModel.value));
    }

    @Override
    public void setValue(String value) {
        relationship.setProperty(ComplianceModel.value, value);
    }
}
