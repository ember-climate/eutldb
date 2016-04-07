package org.sandbag.model.relationships.installations.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.NACECode;

/**
 * Created by root on 07/04/16.
 */
public interface InstallationNACECodeModel extends RelationshipType {

    String LABEL = "INSTALLATION_NACE_CODE";

    NACECode getNACECode();
    Installation getInstallation();
}
