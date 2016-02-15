package org.sandbag.model;

import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.impl.batchimport.cache.idmapping.string.Radix;

/**
 * Created by pablo on 14/02/16.
 */
public class Installation implements InstallationModel{

    Node node = null;

    public Installation(Node node){
        node = node;
    }


    @Override
    public String getId() {
        return String.valueOf(node.getProperty(InstallationModel.id));
    }

    @Override
    public void setId(String id) {
        node.setProperty(InstallationModel.id, id);
    }

    @Override
    public Country getCountry() {
        //node.getProperty()
        return null;
    }

    @Override
    public void setCountry(Country country) {

    }

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(InstallationModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(InstallationModel.name, name);
    }

    @Override
    public void setOpen(boolean open) {
        node.setProperty(InstallationModel.open, open);
    }

    @Override
    public String getCity() {
        return String.valueOf(node.getProperty(InstallationModel.city));
    }

    @Override
    public void setCity(String city) {
        node.setProperty(InstallationModel.city, city);
    }

    @Override
    public String getPostCode() {
        return String.valueOf(node.getProperty(InstallationModel.postCode));
    }

    @Override
    public boolean getOpen() {
        return Boolean.valueOf(String.valueOf(node.getProperty(InstallationModel.open)));
    }

    @Override
    public void setPostCode(String postCode) {
        node.setProperty(InstallationModel.postCode, postCode);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
