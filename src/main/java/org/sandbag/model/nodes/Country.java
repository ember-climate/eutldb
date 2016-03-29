package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.CountryModel;

/**
 * Created by pablo on 14/02/16.
 */
public class Country implements CountryModel {

    Node node = null;

    public Country(Node node){
        this.node = node;
    }

    public String getId() {
        return String.valueOf(node.getProperty(id));
    }

    public void setId(String id) {
        node.setProperty(name, id);

    }

    public String getName() {
        return String.valueOf(node.getProperty(name));
    }

    public void setName(String name) {
        node.setProperty(CountryModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
