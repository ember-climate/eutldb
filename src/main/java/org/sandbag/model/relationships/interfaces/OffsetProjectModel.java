package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Project;

/**
 * Created by root on 29/03/16.
 */
public interface OffsetProjectModel extends RelationshipType{
    String LABEL = "OFFSET_PROJECT";

    Offset getOffset();
    Project getProject();

}
