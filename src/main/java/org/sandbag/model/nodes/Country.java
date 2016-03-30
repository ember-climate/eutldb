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
        return String.valueOf(node.getProperty(CountryModel.id));
    }

    public void setId(String id) {
        node.setProperty(CountryModel.id, id);

    }

    public String getName() {
        return String.valueOf(node.getProperty(CountryModel.name));
    }

    public void setName(String value) {
        node.setProperty(CountryModel.name, value);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
