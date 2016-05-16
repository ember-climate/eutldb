package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.interfaces.CountryModel;
import org.sandbag.model.relationships.Auctioned;
import org.sandbag.model.relationships.aircraft_ops.AircraftOperatorCountry;
import org.sandbag.model.relationships.installations.InstallationCountry;

import java.util.Iterator;

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
    public Iterator<Relationship> getInstallationCountry() {
        return node.getRelationships(new InstallationCountry(null)).iterator();
    }

    @Override
    public Iterator<Relationship> getAircraftOperatorCountry() {
        return node.getRelationships(new AircraftOperatorCountry(null)).iterator();
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public void setAuctionedForPeriod(Period period, String value, String source){
        Auctioned auctioned = new Auctioned(node.createRelationshipTo(period.node, new Auctioned(null)));
        auctioned.setAmount(value);
        auctioned.setSource(source);
    }
}
