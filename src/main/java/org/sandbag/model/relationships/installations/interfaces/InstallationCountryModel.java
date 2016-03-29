package org.sandbag.model.relationships.installations.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Installation;

/**
 * Created by root on 15/02/16.
 */
public interface InstallationCountryModel extends RelationshipType{

    String LABEL = "INSTALLATION_COUNTRY";

    Country getCountry();
    Installation getInstallation();
}
