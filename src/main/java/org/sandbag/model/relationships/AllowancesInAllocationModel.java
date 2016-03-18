package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/03/16.
 */
public interface AllowancesInAllocationModel extends RelationshipType {

    String ARTICLE_10C_TYPE = "article10c";
    String NER_TYPE = "NER";
    String STANDARD_TYPE = "Standard";
    String LABEL = "ALLOWANCES_IN_ALLOCATION";

    String value = "value";
    String type = "type";

    double getValue();
    String getType();
    void setValue(double value);
    void setType(String value);

}
