package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.Company;

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
    String subsidiaryCompany = "subsidiary_company";
    String parentCompany = "parent_company";

    String getName();
    String getRegistrationNumber();
    String getCity();
    String getPostalCode();
    String getAddress();
    String getStatus();
    String getSubsidiaryCompany();
    String getParentCompany();
    //Company getParentCompany();

    void setName(String name);
    void setRegistrationNumber(String name);
    void setPostalCode(String name);
    void setAddress(String address);
    void setCity(String city);
    void setStatus(String status);
    void setSubsidiaryCompany(String value);
    void setParentCompany(String value);
    //void setParentCompany(Company company);

}
