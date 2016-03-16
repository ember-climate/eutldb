package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/03/16.
 */
public interface AllowancesInAllocationModel extends RelationshipType {

    String LABEL = "ALLOWANCES_IN_ALLOCATION";

    String value = "value";

    double getValue();
    void setValue(double value);

}
