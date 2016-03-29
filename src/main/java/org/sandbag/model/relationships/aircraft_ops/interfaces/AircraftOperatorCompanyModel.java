package org.sandbag.model.relationships.aircraft_ops.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.AircraftOperator;
import org.sandbag.model.nodes.Company;

/**
 * Created by root on 18/03/16.
 */
public interface AircraftOperatorCompanyModel  extends RelationshipType {

    String LABEL = "AIRCRAFT_OPERATOR_COMPANY";

    AircraftOperator getAircraftOperator();
    Company getCompany();
}
