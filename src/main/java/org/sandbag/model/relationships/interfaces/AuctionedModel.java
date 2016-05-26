package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/05/16.
 */
public interface AuctionedModel extends RelationshipType {

    String LABEL = "AUCTIONED";

    String amount = "amount";
    String source = "source";

    double getAmount();
    String getSource();
    void setAmount(double amount);
    void setSource(String value);

    Period getPeriod();
    Country getCountry();

}
