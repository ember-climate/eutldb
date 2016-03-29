package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.OffsetModel;
import org.sandbag.model.relationships.OffsetOriginatingCountry;
import org.sandbag.model.relationships.OffsetPeriod;
import org.sandbag.model.relationships.OffsetProject;
import org.sandbag.model.relationships.Offsets;

/**
 * Created by root on 29/03/16.
 */
public class Offset implements OffsetModel {

    Node node = null;

    public Offset(Node node){
        this.node = node;
    }

    @Override
    public double getAmount() {
        return Double.valueOf(String.valueOf(node.getProperty(OffsetModel.amount)));
    }

    @Override
    public void setAmount(double value) {
        node.setProperty(OffsetModel.amount, value);
    }

    @Override
    public void setPeriod(Period period) {
        node.createRelationshipTo(period.node, new OffsetPeriod(null));
    }

    @Override
    public void setOriginatingCountry(Country country) {
        node.createRelationshipTo(country.node, new OffsetOriginatingCountry(null));
    }

    @Override
    public void setProject(Project project) {
        node.createRelationshipTo(project.node, new OffsetProject(null));
    }

    @Override
    public void setInstallation(Installation installation) {
        installation.node.createRelationshipTo(node, new Offsets(null));
    }

    @Override
    public void setAircraftOperator(AircraftOperator aircraftOperator) {
        aircraftOperator.node.createRelationshipTo(node, new Offsets(null));
    }

    @Override
    public String name() {
        return LABEL;
    }

}
