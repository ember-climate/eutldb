package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.FuelTypeModel;

/**
 * Created by root on 18/05/16.
 */
public class FuelType implements FuelTypeModel{

    Node node = null;

    public FuelType(Node node){
        this.node = node;
    }

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(FuelTypeModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(FuelTypeModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
