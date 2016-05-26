package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 17/05/16.
 */
public interface NER300Model extends Label{

    String LABEL = "NER300";

    void setAuctionedForPeriod(Period period, double value, String source);

}

