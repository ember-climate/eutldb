package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.FuelType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.relationships.interfaces.UsesFuelModel;

/**
 * Created by root on 18/05/16.
 */
public class UsesFuel implements UsesFuelModel{

    protected Relationship relationship;

    public UsesFuel(Relationship relationship){
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
    public FuelType getFuelType() {
        return new FuelType(relationship.getEndNode());
    }

    @Override
    public String getNote() {
        return String.valueOf(relationship.getProperty(UsesFuel.note));
    }

    @Override
    public String getSource() {
        return String.valueOf(relationship.getProperty(UsesFuel.source));
    }

    @Override
    public void setNote(String note) {
        relationship.setProperty(UsesFuel.note, note);
    }

    @Override
    public void setSource(String source) {
        relationship.setProperty(UsesFuel.source, source);
    }
}
