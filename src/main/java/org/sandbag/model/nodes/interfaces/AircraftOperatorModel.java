package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.Company;
import org.sandbag.model.nodes.Country;
import org.sandbag.model.nodes.Period;
import org.sandbag.model.nodes.Sector;

/**
 * Created by root on 18/03/16.
 */
public interface AircraftOperatorModel extends Label {

    String LABEL = "AIRCRAFT OPERATOR";
    String id = "id";
    String name = "name";
    String city = "city";
    String postCode = "post_code";
    String status = "status";
    String address = "address";
    String eprtrId = "EPRTR_ID";
    String uniqueCodeUnderCommissionRegulation = "unique_code_under_commission_regulation";
    String monitoringPlanId = "monitoring_plan_id";
    String monitoringPlanFirstYearOfApplicability = "monitoring_first_year_of_applicability";
    String monitoringPlanYearOfExpiry = "monitoring_plan_year_of_expiry";
    String icaoDesignator = "icao_designator";
    String latitude = "latitude";
    String longitude = "longitude";

    String getId();
    Country getCountry();
    Company getCompany();
    String getPostCode();
    String getStatus();
    String getName();
    String getCity();
    String getAddress();
    String getEprtrId();
    String getUniqueCodeUnderCommissionRegulation();
    String getMonitoringPlanId();
    String getMonitoringPlanFirstYearOfApplicability();
    String getMonitoringPlanYearOfExpiry();
    String getIcaoDesignator();
    String getLatitude();
    String getLongitude();
    Sector getSector();


    void setId(String id);
    void setCountry(Country country);
    void setCompany(Company company);
    void setStatus(String status);
    void setCity(String city);
    void setPostCode(String postCode);
    void setName(String name);
    void setAddress(String address);
    void setEprtrId(String eprtrId);
    void setUniqueCodeUnderCommissionRegulation(String value);
    void setMonitoringPlanId(String value);
    void setMonitoringPlanFirstYearOfApplicability(String value);
    void setMonitoringPlanYearOfExpiry(String value);
    void setIcaoDesignator(String value);
    void setLatitude(String latitude);
    void setLongitude(String longitude);
    void setSector(Sector sector);
    void setVerifiedEmissionsForPeriod(Period period, double value);
    void setFreeAllocationForPeriod(Period period, double value, String type);
    void setOffsetsForPeriod(Period period, double value, String type);
    void setOffsetEntitlementForPeriod(Period period, double value);
    void setSurrenderedUnitsForPeriod(Period period, double value);
    void setComplianceForPeriod(Period period, String value);
    void setAllowancesInAllocationForPeriod(Period period, double value, String type);

}
