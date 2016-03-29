package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 23/03/16.
 */
public interface TransactionModel extends RelationshipType {

    String LABEL = "TRANSACTION";

    String value = "value";
    String id = "id";
    String type = "type";
    String status = "status";

    double getValue();
    void setValue(double value);

}
