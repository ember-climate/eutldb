package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.nodes.SandbagSector;
import org.sandbag.model.relationships.interfaces.LegalCapModel;

/**
 * Created by root on 24/05/16.
 */
public class LegalCap implements LegalCapModel {

    protected Relationship relationship;

    public LegalCap(Relationship relationship){
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
    public SandbagSector getSandbagSector(){
        return new SandbagSector(relationship.getStartNode());
    }
    public Country getCountry(){
        return new Country(relationship.getStartNode());
    }

    @Override
    public String getAmount() {
        return String.valueOf(relationship.getProperty(LegalCapModel.amount));
    }

    @Override
    public String getSource() {
        return String.valueOf(relationship.getProperty(LegalCapModel.source));
    }

    @Override
    public void setAmount(String amount) {
        relationship.setProperty(LegalCapModel.amount, amount);
    }

    @Override
    public void setSource(String source) {
        relationship.setProperty(LegalCapModel.source, source);
    }
}
