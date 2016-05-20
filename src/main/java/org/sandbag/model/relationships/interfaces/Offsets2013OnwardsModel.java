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

    Country getCountry();
    Period getPeriod();
}
