package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/02/16.
 */
public interface VerifiedEmissionsModel extends RelationshipType {

    String LABEL = "VERIFIED_EMISSIONS";

    String value = "value";

    double getValue();
    void setValue(double value);

    Installation getInstallation();
    Period getPeriod();

}
