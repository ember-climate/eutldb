package org.sandbag.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.impl.batchimport.cache.idmapping.string.Radix;

/**
 * Created by pablo on 14/02/16.
 */
public class Installation implements InstallationModel{

    Node node = null;

    public Installation(Node node){
        this.node = node;
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
        return new Country(node.getSingleRelationship(new InstallationCountry(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public Company getCompany() {
        return new Company(node.getSingleRelationship(new InstallationCompany(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public void setCountry(Country country) {
        node.createRelationshipTo(country.node, new InstallationCountry(null));
    }

    @Override
    public void setCompany(Company company) { node.createRelationshipTo(company.node, new InstallationCompany(null));}

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(InstallationModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(InstallationModel.name, name);
    }

    @Override
    public void setSector(Sector sector) {
        node.createRelationshipTo(sector.node, new InstallationSector(null));
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
    public Sector getSector() {
        return new Sector(node.getSingleRelationship(new InstallationSector(null), Direction.OUTGOING).getEndNode());
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
    public void setVerifiedEmissionsForPeriod(Period period, double value){
        VerifiedEmissions verifiedEmissions = new VerifiedEmissions(node.createRelationshipTo(period.node, new VerifiedEmissions(null)));
        verifiedEmissions.setValue(value);
    }
    @Override
    public void setFreeAllocationForPeriod(Period period, double value, String type){
        FreeAllocation freeAllocation = new FreeAllocation(node.createRelationshipTo(period.node, new FreeAllocation(null)));
        freeAllocation.setValue(value);
        freeAllocation.setType(type);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
