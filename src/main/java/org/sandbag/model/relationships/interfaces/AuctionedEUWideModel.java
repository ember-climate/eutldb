package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 27/07/16.
 */
public interface AuctionedEUWideModel extends RelationshipType {

    String LABEL = "AUCTIONED_EU_WIDE";

    String AUCTIONED_TYPE_AVIATION = "aviation";
    String AUCTIONED_TYPE_INSTALLATIONS = "installations";
    String AUCTIONED_TYPE_ALL = "all";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double amount);
    void setType(String value);

    Period getPeriod();
    Country getCountry();

}
