package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/03/16.
 */
public interface SurrenderedUnitsModel extends RelationshipType {

    String LABEL = "SURRENDERED_UNITS";

    String value = "value";

    double getValue();
    void setValue(double value);

    Period getPeriod();

}
