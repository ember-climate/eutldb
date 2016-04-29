package org.sandbag.model.relationships.aircraft_ops.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.NACECode;

/**
 * Created by root on 28/04/16.
 */
public interface AircraftOperatorNACECodeModel extends RelationshipType {

    String LABEL = "AIRCRAFT_OPERATOR_NACE_CODE";

    NACECode getNACECode();
    AircraftOperator getAircraftOperator();
}
