package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 24/05/16.
 */
public interface LegalCapModel extends RelationshipType {

    String LABEL = "LEGAL_CAP";

    String amount = "amount";
    String source = "source";

    String getAmount();
    String getSource();
    void setAmount(String amount);
    void setSource(String value);

    Period getPeriod();

}
