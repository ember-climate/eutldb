package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.FuelType;

/**
 * Created by root on 18/05/16.
 */
public interface UsesFuelModel extends RelationshipType {

    String LABEL = "USES_FUEL";

    String note = "note";
    String source = "source";

    String getNote();
    String getSource();
    void setNote(String note);
    void setSource(String value);

    FuelType getFuelType();
}
