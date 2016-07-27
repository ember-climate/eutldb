package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 27/07/16.
 */
public interface LegalCapEUWideModel extends RelationshipType {

    String LABEL = "LEGAL_CAP_EU_WIDE";

    String LEGAL_CAP_TYPE_ALL = "all";
    String LEGAL_CAP_TYPE_INSTALLATIONS = "installations";
    String LEGAL_CAP_TYPE_AVIATION = "aviation";

    String value = "value";
    String source = "source";
    String type = "type";

    double getValue();
    String getSource();
    String getType();
    void setValue(double value);
    void setSource(String value);
    void setType(String value);

    Period getPeriod();
}
