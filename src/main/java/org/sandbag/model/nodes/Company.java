package org.sandbag.model.nodes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.CompanyModel;
import org.sandbag.model.relationships.ParentCompany;

/**
 * Created by root on 15/02/16.
 */
public class Company implements CompanyModel {

    Node node = null;

    public Company(Node node){
        this.node = node;
    }


    @Override
    public String getName() {
        return String.valueOf(node.getProperty(CompanyModel.name));
    }

    @Override
    public String getRegistrationNumber() {
        return String.valueOf(node.getProperty(CompanyModel.registrationNumber));
    }

    @Override
    public String getCity() {
        return String.valueOf(node.getProperty(CompanyModel.city));
    }

    @Override
    public String getPostalCode() {
        return String.valueOf(node.getProperty(CompanyModel.postalCode));
    }

    @Override
    public String getAddress() {
        return String.valueOf(node.getProperty(CompanyModel.address));
    }

    @Override
    public String getStatus() {
        return String.valueOf(node.getProperty(CompanyModel.status));
    }

    @Override
    public Company getParentCompany(){
        return new Company(node.getSingleRelationship(new ParentCompany(null), Direction.OUTGOING).getEndNode());

    }

    @Override
    public void setName(String name) {
        node.setProperty(CompanyModel.name, name);
    }

    @Override
    public void setRegistrationNumber(String value) {
        node.setProperty(CompanyModel.registrationNumber, value);
    }

    @Override
    public void setPostalCode(String value) {
        node.setProperty(CompanyModel.postalCode, value);
    }

    @Override
    public void setAddress(String address) {
        node.setProperty(CompanyModel.address, address);
    }

    @Override
    public void setCity(String city) {
        node.setProperty(CompanyModel.city, city);
    }

    @Override
    public void setStatus(String status) {
        node.setProperty(CompanyModel.status, status);
    }

    @Override
    public void setParentCompany(Company company){
        node.createRelationshipTo(company.node, new ParentCompany(null));
    }

    @Override
    public String name() {
        return LABEL;
    }
}
