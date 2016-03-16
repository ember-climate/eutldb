package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/03/16.
 */
public interface SurrenderedUnitsModel extends RelationshipType {

    String LABEL = "SURRENDERED_UNITS";

    String value = "value";

    double getValue();
    void setValue(double value);

}
