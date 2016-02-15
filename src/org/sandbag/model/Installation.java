package org.sandbag.model;

/**
 * Created by pablo on 14/02/16.
 */
public class Installation implements InstallationModel{

    protected String id;
    protected Country country;
    protected boolean open;
    protected String name;
    protected String city;
    protected String postCode;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    @Override
    public boolean getOpen() {
        return false;
    }

    @Override
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String name() {
        return null;
    }
}
