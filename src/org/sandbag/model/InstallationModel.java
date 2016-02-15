package org.sandbag.model;

/**
 * Created by pablo on 15/02/16.
 */
public interface InstallationModel {

    static final String LABEL = "installation";

    String getId();
    Country getCountry();
    String getPostCode();
    boolean getOpen();
    String getName();
    String getCity();


    void setId(String id);
    void setCountry(Country country);
    void setOpen(boolean open);
    void setCity(String city);
    void setPostCode(String postCode);
    void setName(String name);

    String name();
}
