package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;

/**
 * Created by root on 16/02/16.
 */
public class Period implements PeriodModel {

    Node node = null;

    public Period(Node node){
        this.node = node;
    }

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(PeriodModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(PeriodModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }

}
