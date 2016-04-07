package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.NACECodeModel;

/**
 * Created by root on 03/03/16.
 */
public class NACECode implements NACECodeModel {

    Node node = null;

    public NACECode(Node node){
        this.node = node;
    }

    @Override
    public String getId() {
        return String.valueOf(node.getProperty(NACECodeModel.id));
    }

    @Override
    public String getDescription() {
        return String.valueOf(node.getProperty(NACECodeModel.description));
    }

    @Override
    public void setId(String id) {
        node.setProperty(NACECodeModel.id, id);
    }

    @Override
    public void setDescription(String value) {
        node.setProperty(NACECodeModel.description, value);
    }


    @Override
    public String name() {
        return LABEL;
    }
}

