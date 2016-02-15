package org.sandbag.model;

import org.neo4j.graphdb.Node;

/**
 * Created by pablo on 14/02/16.
 */
public class Country implements CountryModel{

    Node node = null;

    public Country(Node node){
        node = node;
    }

    public String getId() {
        return String.valueOf(node.getProperty(CountryModel.id));
    }

    public void setId(String id) {
        node.setProperty(CountryModel.name, id);

    }

    public String getName() {
        return String.valueOf(node.getProperty(CountryModel.name));
    }

    public void setName(String name) {
        node.setProperty(CountryModel.name, name);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
