package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/03/16.
 */
public interface ComplianceModel extends RelationshipType {

    String LABEL = "COMPLIANCE";

    String value = "value";

    String getValue();
    void setValue(String value);

    Installation getInstallation();
    Period getPeriod();

}