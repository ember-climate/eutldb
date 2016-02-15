package org.sandbag.model;

import org.neo4j.graphdb.Node;

/**
 * Created by pablo on 14/02/16.
 */
public class Sector implements SectorModel {

    Node node = null;

    public Sector(Node node){
        node = node;
    }

    public String getName() {
        return String.valueOf(node.getProperty(InstallationModel.name));
    }

    public void setName(String name) {
        node.setProperty(InstallationModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
