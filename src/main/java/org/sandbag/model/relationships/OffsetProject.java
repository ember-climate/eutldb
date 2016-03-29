package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.nodes.Project;
import org.sandbag.model.relationships.interfaces.OffsetProjectModel;

/**
 * Created by root on 29/03/16.
 */
public class OffsetProject implements OffsetProjectModel {

    protected Relationship relationship;

    public OffsetProject(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Project getProject(){
        return new Project(relationship.getEndNode());
    }

    @Override
    public Offset getOffset(){
        return new Offset(relationship.getStartNode());
    }
}
