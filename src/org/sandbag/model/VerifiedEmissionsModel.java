package org.sandbag.model;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/02/16.
 */
public interface VerifiedEmissionsModel extends RelationshipType {

    String LABEL = "VERIFIED_EMISSIONS";

    String value = "value";

    String getValue();
    void setValue(double value);

}
