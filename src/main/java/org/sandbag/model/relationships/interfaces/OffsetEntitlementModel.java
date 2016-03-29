package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public interface OffsetEntitlementModel extends RelationshipType {

    String LABEL = "OFFSET_ENTITLEMENT";

    String value = "value";

    double getValue();
    void setValue(double value);

    Period getPeriod();
}
