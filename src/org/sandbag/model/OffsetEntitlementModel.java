package org.sandbag.model;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 17/02/16.
 */
public interface OffsetEntitlementModel extends RelationshipType {

    String LABEL = "OFFSET_ENTITLEMENT";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String type);
}
