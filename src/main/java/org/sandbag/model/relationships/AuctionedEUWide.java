package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.AuctionedEUWideModel;

/**
 * Created by root on 27/07/16.
 */
public class AuctionedEUWide implements AuctionedEUWideModel {

    protected Relationship relationship;

    public AuctionedEUWide(Relationship relationship){
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
    @Override
    public Country getCountry() { return new Country(relationship.getStartNode()); }

    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(AuctionedEUWideModel.value)));
    }

    @Override
    public String getType() {
        return String.valueOf(relationship.getProperty(AuctionedEUWideModel.type));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(AuctionedEUWideModel.value, value);
    }

    @Override
    public void setType(String value) {
        relationship.setProperty(AuctionedEUWideModel.type, value);
    }
}
