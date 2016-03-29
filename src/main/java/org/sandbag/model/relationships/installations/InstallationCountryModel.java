package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 15/02/16.
 */
public interface InstallationCountryModel extends RelationshipType{

    String LABEL = "INSTALLATION_COUNTRY";
}
