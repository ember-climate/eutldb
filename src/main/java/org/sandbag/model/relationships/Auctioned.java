package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.relationships.interfaces.AuctionedModel;

/**
 * Created by root on 16/05/16.
 */
public class Auctioned implements AuctionedModel {

    protected Relationship relationship;

    public Auctioned(Relationship relationship){
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
    public String getAmount() {
        return String.valueOf(relationship.getProperty(AuctionedModel.amount));
    }

    @Override
    public String getSource() {
        return String.valueOf(relationship.getProperty(AuctionedModel.source));
    }

    @Override
    public void setAmount(String amount) {
        relationship.setProperty(AuctionedModel.amount, amount);
    }

    @Override
    public void setSource(String source) {
        relationship.setProperty(AuctionedModel.source, source);
    }
}
