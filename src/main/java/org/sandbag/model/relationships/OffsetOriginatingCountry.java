package org.sandbag.model.relationships;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Offset;
import org.sandbag.model.relationships.interfaces.OffsetOriginatingCountryModel;

/**
 * Created by root on 29/03/16.
 */
public class OffsetOriginatingCountry implements OffsetOriginatingCountryModel {

    protected Relationship relationship;

    public OffsetOriginatingCountry(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public Country getCountry(){
        return new Country(relationship.getEndNode());
    }

    @Override
    public Offset getOffset(){
        return new Offset(relationship.getStartNode());
    }
}
