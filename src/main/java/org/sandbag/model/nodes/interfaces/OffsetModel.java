package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.*;

/**
 * Created by root on 29/03/16.
 */
public interface OffsetModel extends Label {

    String LABEL = "OFFSET";
    String CER_UNIT_TYPE = "CER";
    String AAU_UNIT_TYPE = "AAU";
    String ERU_UNIT_TYPE = "ERU";
    String GENERAL_ALLOWANCES_UNIT_TYPE = "General allowances";
    String AVIATION_ALLOWANCES_UNIT_TYPE = "Aviation allowances";

    String amount = "amount";
    String unitType = "unit_type";

    double getAmount();
    String getUnitType();
    Country getOriginatingCountry();
    Period getPeriod();
    Project getProject();

    void setAmount(double value);
    void setUnitType(String value);

    void setPeriod(Period period);
    void setOriginatingCountry(Country country);
    void setProject(Project project);
    void setInstallation(Installation installation);
    void setAircraftOperator(AircraftOperator aircraftOperator);

}
