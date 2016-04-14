package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.SandbagSectorModel;

/**
 * Created by root on 14/04/16.
 */
public class SandbagSector implements SandbagSectorModel {

    Node node = null;

    public SandbagSector(Node node){
        this.node = node;
    }

    public String getName() {
        return String.valueOf(node.getProperty(SandbagSectorModel.name));
    }
    public String getId() {
        return String.valueOf(node.getProperty(SandbagSectorModel.id));
    }

    public void setName(String name) {
        node.setProperty(SandbagSectorModel.name, name);
    }
    public void setId(String value) {
        node.setProperty(SandbagSectorModel.id, value);
    }


    @Override
    public String name() {
        return LABEL;
    }
}


