package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by root on 15/02/16.
 */
public interface CompanyModel extends Label {

    String LABEL = "COMPANY";
    String name = "name";
    String registrationNumber = "registration_number";
    String postalCode = "postal_code";
    String city = "city";
    String address = "address";
    String status = "status";

    String getName();
    String getRegistrationNumber();
    String getCity();
    String getPostalCode();
    String getAddress();
    String getStatus();
    Company getParentCompany();

    void setName(String name);
    void setRegistrationNumber(String name);
    void setPostalCode(String name);
    void setAddress(String address);
    void setCity(String city);
    void setStatus(String status);
    void setParentCompany(Company company);

}
