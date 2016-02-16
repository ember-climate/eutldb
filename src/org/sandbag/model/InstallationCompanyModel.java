package org.sandbag.model;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/02/16.
 */
public interface InstallationCompanyModel extends RelationshipType {

    String LABEL = "INSTALLATION_COMPANY";
}
