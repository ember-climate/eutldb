package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 27/07/16.
 */
public interface OffsetEntitlementsEUWideModel extends RelationshipType {

    String LABEL = "OFFSET_ENTITLEMENT_EU_WIDE";

    String OFFSET_ENTITLEMENTS_TYPE_ALL = "all";
    String OFFSET_ENTITLEMENTS_TYPE_AVIATION = "aviation";
    String OFFSET_ENTITLEMENTS_TYPE_INSTALLATIONS = "installations";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String type);

    Period getPeriod();
}
