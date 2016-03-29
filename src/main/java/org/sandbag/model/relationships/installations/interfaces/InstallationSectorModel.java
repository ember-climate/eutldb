package org.sandbag.model.relationships.installations.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Sector;

/**
 * Created by root on 16/02/16.
 */
public interface InstallationSectorModel extends RelationshipType {

    String LABEL = "INSTALLATION_SECTOR";

    Sector getSector();
    Installation getInstallation();

}
