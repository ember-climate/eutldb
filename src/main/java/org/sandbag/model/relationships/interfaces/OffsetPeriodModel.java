package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 29/03/16.
 */
public interface OffsetPeriodModel extends RelationshipType{
    String LABEL = "OFFSET_PERIOD";

    Offset getOffset();
    Period getPeriod();

}
