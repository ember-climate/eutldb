package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 27/07/16.
 */
public interface OffsetsEUWideModel extends RelationshipType {

    String LABEL = "OFFSETS_EU_WIDE";

    String OFFSETS_TYPE_ALL = "all";
    String OFFSETS_TYPE_AVIATION = "aviation";
    String OFFSETS_TYPE_INSTALLATIONS = "installations";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String value);
}
