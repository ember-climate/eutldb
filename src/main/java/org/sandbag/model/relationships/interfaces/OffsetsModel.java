package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public interface OffsetsModel extends RelationshipType {

    String LABEL = "OFFSETS";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String type);

    Period getPeriod();
}
