package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 17/02/16.
 */
public interface OffsetEntitlementModel extends RelationshipType {

    String LABEL = "OFFSET_ENTITLEMENT";

    String value = "value";

    double getValue();
    void setValue(double value);
}
