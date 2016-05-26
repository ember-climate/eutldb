package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.NER300Model;
import org.sandbag.model.relationships.Auctioned;

/**
 * Created by root on 17/05/16.
 */
public class NER300 implements NER300Model{

    Node node = null;

    public NER300(Node node){
        this.node = node;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public void setAuctionedForPeriod(Period period, double value, String source){
        Auctioned auctioned = new Auctioned(node.createRelationshipTo(period.node, new Auctioned(null)));
        auctioned.setAmount(value);
        auctioned.setSource(source);
    }
}
