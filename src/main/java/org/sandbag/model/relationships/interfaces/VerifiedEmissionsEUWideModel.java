package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 27/07/16.
 */
public interface VerifiedEmissionsEUWideModel extends RelationshipType {

    String EMISSIONS_TYPE_AVIATION = "aviation";
    String EMISSIONS_TYPE_INSTALLATIONS = "installations";
    String EMISSIONS_TYPE_ALL = "all";

    String LABEL = "VERIFIED_EMISSIONS_EU_WIDE";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String value);

    Period getPeriod();

}
