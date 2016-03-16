package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/02/16.
 */
public interface FreeAllocationModel extends RelationshipType{

    String LABEL = "FREE_ALLOCATION";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();

    void setType(String type);
    void setValue(double value);

    Installation getInstallation();
    Period getPeriod();

}
