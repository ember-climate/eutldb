package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.interfaces.ProjectModel;
import org.sandbag.model.relationships.OffsetProject;

import java.util.Iterator;
import java.util.List;

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

    @Override
    public Iterator<Relationship> getOffsetProject(){
        return  node.getRelationships(new OffsetProject(null)).iterator();
    }

}

