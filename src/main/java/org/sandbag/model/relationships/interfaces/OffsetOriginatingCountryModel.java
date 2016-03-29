package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Offset;

/**
 * Created by root on 29/03/16.
 */
public interface OffsetOriginatingCountryModel extends RelationshipType {

    String LABEL = "OFFSET_ORIGINATING_COUNTRY";

    Country getCountry();
    Offset getOffset();

}