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
    String type = "type";

    double getAmount();
    String getSource();
    String getType();
    void setAmount(double amount);
    void setSource(String value);
    void setType(String value);

    Period getPeriod();
    Country getCountry();

}
