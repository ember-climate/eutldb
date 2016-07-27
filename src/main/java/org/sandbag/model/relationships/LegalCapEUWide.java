package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.nodes.SandbagSector;
import org.sandbag.model.relationships.interfaces.LegalCapEUWideModel;
import org.sandbag.model.relationships.interfaces.LegalCapModel;

/**
 * Created by root on 27/07/16.
 */
public class LegalCapEUWide implements LegalCapEUWideModel{

    protected Relationship relationship;

    public LegalCapEUWide(Relationship relationship){
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
    public Country getCountry(){
        return new Country(relationship.getStartNode());
    }

    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(LegalCapEUWideModel.value)));
    }
    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(LegalCapEUWideModel.type));
    }

    @Override
    public String getSource() {
        return String.valueOf(relationship.getProperty(LegalCapEUWideModel.source));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(LegalCapEUWideModel.value, value);
    }

    @Override
    public void setSource(String source) {
        relationship.setProperty(LegalCapEUWideModel.source, source);
    }

    @Override
    public void setType(String type) {
        relationship.setProperty(LegalCapEUWideModel.type, type);
    }
}
