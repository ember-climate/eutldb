package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.NACECode;
import org.sandbag.model.relationships.installations.interfaces.InstallationNACECodeModel;

/**
 * Created by root on 07/04/16.
 */
public class InstallationNACECode implements InstallationNACECodeModel {

    protected Relationship relationship;

    public InstallationNACECode(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    @Override
    public NACECode getNACECode(){
        return new NACECode(relationship.getEndNode());
    }
}
