package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 20/05/16.
 */
public interface Offsets2013OnwardsModel extends RelationshipType
{
    String LABEL = "OFFSETS_2013_ONWARDS";

    String type = "type";
    String reference = "reference";

    String getType();
    String getReference();
    void setType(String value);
    void setReference(String value);

    Country getCountry();
    Period getPeriod();
}
