package org.sandbag.model.relationships.aircraft_ops;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 18/03/16.
 */
public interface AircraftOperatorSectorModel extends RelationshipType {

    String LABEL = "AIRCRAFT_OPERATOR_SECTOR";

}
