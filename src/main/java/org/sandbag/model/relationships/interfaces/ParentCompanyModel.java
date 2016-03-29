package org.sandbag.model.relationships.interfaces;

import org.neo4j.graphdb.RelationshipType;
import org.sandbag.model.nodes.Company;

/**
 * Created by root on 16/02/16.
 */
public interface ParentCompanyModel extends RelationshipType{
    String LABEL = "PARENT_COMPANY";

    String name = "name";

    Company getParentCompany();
    Company getCompany();

}
