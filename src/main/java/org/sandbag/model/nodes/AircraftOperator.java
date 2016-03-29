package org.sandbag.model.nodes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.sandbag.model.nodes.interfaces.AircraftOperatorModel;
import org.sandbag.model.relationships.*;
import org.sandbag.model.relationships.aircraft_ops.AircraftOperatorCompany;
import org.sandbag.model.relationships.aircraft_ops.AircraftOperatorCountry;
import org.sandbag.model.relationships.aircraft_ops.AircraftOperatorSector;

/**
 * Created by root on 18/03/16.
 */
public class AircraftOperator implements AircraftOperatorModel {

    Node node = null;

    public AircraftOperator(Node node){
        this.node = node;
    }


    @Override
    public String getId() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.id));
    }

    @Override
    public void setId(String id) {
        node.setProperty(AircraftOperatorModel.id, id);
    }

    @Override
    public Country getCountry() {
        return new Country(node.getSingleRelationship(new AircraftOperatorCountry(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public Company getCompany() {
        return new Company(node.getSingleRelationship(new AircraftOperatorCompany(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public void setCountry(Country country) {
        node.createRelationshipTo(country.node, new AircraftOperatorCountry(null));
    }

    @Override
    public void setCompany(Company company) { node.createRelationshipTo(company.node, new AircraftOperatorCompany(null));}

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(AircraftOperatorModel.name, name);
    }

    @Override
    public void setAddress(String address) {
        node.setProperty(AircraftOperatorModel.address, address);
    }

    @Override
    public void setEprtrId(String eprtrId) {
        node.setProperty(AircraftOperatorModel.eprtrId, eprtrId);
    }

    @Override
    public void setUniqueCodeUnderCommissionRegulation(String value) {
        node.setProperty(AircraftOperatorModel.uniqueCodeUnderCommissionRegulation, value);
    }

    @Override
    public void setMonitoringPlanId(String value) {
        node.setProperty(AircraftOperatorModel.monitoringPlanId, value);
    }

    @Override
    public void setMonitoringPlanFirstYearOfApplicability(String value) {
        node.setProperty(AircraftOperatorModel.monitoringPlanFirstYearOfApplicability, value);
    }

    @Override
    public void setMonitoringPlanYearOfExpiry(String value) {
        node.setProperty(AircraftOperatorModel.monitoringPlanYearOfExpiry, value);
    }

    @Override
    public void setIcaoDesignator(String value) {
        node.setProperty(AircraftOperatorModel.icaoDesignator, value);
    }

    @Override
    public void setLatitude(String latitude) {
        node.setProperty(AircraftOperatorModel.latitude, latitude);
    }

    @Override
    public void setLongitude(String longitude) {
        node.setProperty(AircraftOperatorModel.longitude, longitude);
    }

    @Override
    public void setSector(Sector sector) {
        node.createRelationshipTo(sector.node, new AircraftOperatorSector(null));
    }

    @Override
    public void setStatus(String status) {
        node.setProperty(AircraftOperatorModel.status, status);
    }

    @Override
    public String getCity() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.city));
    }

    @Override
    public String getAddress() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.address));
    }

    @Override
    public String getEprtrId() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.eprtrId));
    }

    @Override
    public String getUniqueCodeUnderCommissionRegulation() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.uniqueCodeUnderCommissionRegulation));
    }

    @Override
    public String getMonitoringPlanId() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.monitoringPlanId));
    }

    @Override
    public String getMonitoringPlanFirstYearOfApplicability() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.monitoringPlanFirstYearOfApplicability));
    }

    @Override
    public String getMonitoringPlanYearOfExpiry() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.monitoringPlanYearOfExpiry));
    }

    @Override
    public String getIcaoDesignator() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.icaoDesignator));
    }

    @Override
    public String getLatitude() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.latitude));
    }

    @Override
    public String getLongitude() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.longitude));
    }

    @Override
    public Sector getSector() {
        return new Sector(node.getSingleRelationship(new AircraftOperatorSector(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public void setCity(String city) {
        node.setProperty(AircraftOperatorModel.city, city);
    }

    @Override
    public String getPostCode() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.postCode));
    }

    @Override
    public String getStatus() {
        return String.valueOf(node.getProperty(AircraftOperatorModel.status));
    }

    @Override
    public void setPostCode(String postCode) {
        node.setProperty(AircraftOperatorModel.postCode, postCode);
    }

    @Override
    public void setVerifiedEmissionsForPeriod(Period period, double value){
        VerifiedEmissions verifiedEmissions = new VerifiedEmissions(node.createRelationshipTo(period.node, new VerifiedEmissions(null)));
        verifiedEmissions.setValue(value);
    }
    @Override
    public void setFreeAllocationForPeriod(Period period, double value, String type){
        FreeAllocation freeAllocation = new FreeAllocation(node.createRelationshipTo(period.node, new FreeAllocation(null)));
        freeAllocation.setValue(value);
        freeAllocation.setType(type);
    }
    @Override
    public void setOffsetsForPeriod(Period period, double value, String type){
        Offsets offsets = new Offsets(node.createRelationshipTo(period.node, new Offsets(null)));
        offsets.setValue(value);
        offsets.setType(type);
    }
    @Override
    public void setOffsetEntitlementForPeriod(Period period, double value){
        OffsetEntitlement offsetEntitlement = new OffsetEntitlement(node.createRelationshipTo(period.node, new OffsetEntitlement(null)));
        offsetEntitlement.setValue(value);
    }

    @Override
    public void setSurrenderedUnitsForPeriod(Period period, double value) {
        SurrenderedUnits surrenderedUnits = new SurrenderedUnits(node.createRelationshipTo(period.node, new SurrenderedUnits(null)));
        surrenderedUnits.setValue(value);
    }

    @Override
    public void setComplianceForPeriod(Period period, String value) {
        Compliance compliance = new Compliance(node.createRelationshipTo(period.node, new Compliance(null)));
        compliance.setValue(value);
    }

    @Override
    public void setAllowancesInAllocationForPeriod(Period period, double value, String type) {
        AllowancesInAllocation allowancesInAllocation = new AllowancesInAllocation(node.createRelationshipTo(period.node, new AllowancesInAllocation(null)));
        allowancesInAllocation.setValue(value);
        allowancesInAllocation.setType(type);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
