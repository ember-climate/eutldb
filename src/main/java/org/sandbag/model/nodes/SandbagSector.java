package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.SandbagSectorModel;
import org.sandbag.model.relationships.AggregatesSector;
import org.sandbag.model.relationships.LegalCap;

/**
 * Created by root on 14/04/16.
 */
public class SandbagSector implements SandbagSectorModel {

    Node node = null;

    public SandbagSector(Node node){
        this.node = node;
    }

    public String getName() {
        return String.valueOf(node.getProperty(SandbagSectorModel.name));
    }
    public String getId() {
        return String.valueOf(node.getProperty(SandbagSectorModel.id));
    }

    public void setName(String value) {
        node.setProperty(SandbagSectorModel.name, value);
    }
    public void setId(String value) {
        node.setProperty(SandbagSectorModel.id, value);
    }


    @Override
    public String name() {
        return LABEL;
    }

    public void setAggregatesSector(Sector sector){
        node.createRelationshipTo(sector.node, new AggregatesSector(null));
    }

    public void setLegalCap(Period period, String amount, String source){
        LegalCap legalCap = new LegalCap(node.createRelationshipTo(period.node, new LegalCap(null)));
        legalCap.setAmount(amount);
        legalCap.setSource(source);
    }
}


