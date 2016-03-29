package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.*;

/**
 * Created by root on 29/03/16.
 */
public interface OffsetModel extends Label {

    String LABEL = "OFFSET";
    String amount = "amount";

    double getAmount();
    void setAmount(double value);

    void setPeriod(Period period);
    void setOriginatingCountry(Country country);
    void setProject(Project project);
    void setInstallation(Installation installation);
    void setAircraftOperator(AircraftOperator aircraftOperator);

}
