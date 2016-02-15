package org.sandbag.model;

/**
 * Created by pablo on 14/02/16.
 */
public class Sector implements SectorModel {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return LABEL;
    }
}
