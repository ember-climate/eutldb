package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by pablo on 15/02/16.
 */
public interface InstallationModel extends Label{

    String LABEL = "INSTALLATION";
    String id = "id";
    String name = "name";
    String city = "city";
    String postCode = "post_code";
    String open = "open";

    String getId();
    Country getCountry();
    Company getCompany();
    String getPostCode();
    boolean getOpen();
    String getName();
    String getCity();
    Sector getSector();


    void setId(String id);
    void setCountry(Country country);
    void setCompany(Company company);
    void setOpen(boolean open);
    void setCity(String city);
    void setPostCode(String postCode);
    void setName(String name);
    void setSector(Sector sector);
    void setVerifiedEmissionsForPeriod(Period period, double value);
    void setFreeAllocationForPeriod(Period period, double value, String type);
    void setOffsetsForPeriod(Period period, double value, String type);
    void setOffsetEntitlementForPeriod(Period period, double value, String type);

}
