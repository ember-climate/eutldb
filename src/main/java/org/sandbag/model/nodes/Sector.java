package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.InstallationModel;
import org.sandbag.model.nodes.interfaces.SectorModel;

/**
 * Created by pablo on 14/02/16.
 */
public class Sector implements SectorModel {

    Node node = null;

    public Sector(Node node){
        this.node = node;
    }

    public String getName() {
        return String.valueOf(node.getProperty(SectorModel.name));
    }
    public String getId() {
        return String.valueOf(node.getProperty(SectorModel.id));
    }

    public void setName(String name) {
        node.setProperty(SectorModel.name, name);
    }
    public void setId(String id) {
        node.setProperty(SectorModel.id, id);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
