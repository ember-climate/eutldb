package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.ProjectModel;

/**
 * Created by root on 29/03/16.
 */
public class Project implements ProjectModel {

    Node node = null;

    public Project(Node node){
        this.node = node;
    }

    @Override
    public String getId() {
        return String.valueOf(node.getProperty(ProjectModel.id));
    }

    @Override
    public void setId(String id) {
        node.setProperty(ProjectModel.id, id);
    }

    @Override
    public String name() {
        return LABEL;
    }

}

