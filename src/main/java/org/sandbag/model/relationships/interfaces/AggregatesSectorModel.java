package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.SandbagSector;
import org.sandbag.model.nodes.Sector;

/**
 * Created by root on 14/04/16.
 */
public interface AggregatesSectorModel extends RelationshipType{
    String LABEL = "AGGREGATES_SECTOR";

    SandbagSector getSandbagSector();
    Sector getSector();

}
