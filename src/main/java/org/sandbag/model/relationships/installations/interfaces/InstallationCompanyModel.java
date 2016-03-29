package org.sandbag.model.relationships.installations.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Company;
import org.sandbag.model.nodes.Installation;

/**
 * Created by root on 16/02/16.
 */
public interface InstallationCompanyModel extends RelationshipType {

    String LABEL = "INSTALLATION_COMPANY";

    Installation getInstallation();
    Company getCompany();
}
