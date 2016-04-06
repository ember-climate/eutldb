package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;

import java.util.Iterator;

/**
 * Created by root on 29/03/16.
 */
public interface ProjectModel extends Label {

    String LABEL = "PROJECT";
    String id = "id";

    String getId();
    void setId(String id);

    Iterator<Relationship> getOffsetProject();

}
