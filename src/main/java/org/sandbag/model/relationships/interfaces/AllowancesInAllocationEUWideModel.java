package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 27/07/16.
 */
public interface AllowancesInAllocationEUWideModel extends RelationshipType {

    String LABEL = "ALLOWANCES_IN_ALLOCATION_EU_WIDE";

    String ALLOWANCES_IN_ALLOCATION_TYPE_ALL = "all";
    String ALLOWANCES_IN_ALLOCATION_TYPE_INSTALLATIONS = "installations";
    String ALLOWANCES_IN_ALLOCATION_TYPE_AVIATION = "aviation";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String value);

    Period getPeriod();

}
