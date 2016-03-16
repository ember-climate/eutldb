package org.sandbag.model.nodes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.sandbag.model.relationships.*;

/**
 * Created by pablo on 14/02/16.
 */
public class Installation implements InstallationModel{

    Node node = null;

    public Installation(Node node){
        this.node = node;
    }


    @Override
    public String getId() {
        return String.valueOf(node.getProperty(InstallationModel.id));
    }

    @Override
    public void setId(String id) {
        node.setProperty(InstallationModel.id, id);
    }

    @Override
    public Country getCountry() {
        return new Country(node.getSingleRelationship(new InstallationCountry(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public Company getCompany() {
        return new Company(node.getSingleRelationship(new InstallationCompany(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public void setCountry(Country country) {
        node.createRelationshipTo(country.node, new InstallationCountry(null));
    }

    @Override
    public void setCompany(Company company) { node.createRelationshipTo(company.node, new InstallationCompany(null));}

    @Override
    public String getName() {
        return String.valueOf(node.getProperty(InstallationModel.name));
    }

    @Override
    public void setName(String name) {
        node.setProperty(InstallationModel.name, name);
    }

    @Override
    public void setAddress(String address) {
        node.setProperty(InstallationModel.address, address);
    }

    @Override
    public void setEprtrId(String eprtrId) {
        node.setProperty(InstallationModel.eprtrId, eprtrId);
    }

    @Override
    public void setPermitId(String permitId) {
        node.setProperty(InstallationModel.permitId, permitId);
    }

    @Override
    public void setPermitEntryDate(String date) {
        node.setProperty(InstallationModel.permitEntryDate, date);
    }

    @Override
    public void setPermitExpiryOrRevocationDate(String date) {
        node.setProperty(InstallationModel.permitExpiryOrRevocationDate, date);
    }

    @Override
    public void setLatitude(String latitude) {
        node.setProperty(InstallationModel.latitude, latitude);
    }

    @Override
    public void setLongitude(String longitude) {
        node.setProperty(InstallationModel.longitude, longitude);
    }

    @Override
    public void setSector(Sector sector) {
        node.createRelationshipTo(sector.node, new InstallationSector(null));
    }

    @Override
    public void setOpen(boolean open) {
        node.setProperty(InstallationModel.open, open);
    }

    @Override
    public String getCity() {
        return String.valueOf(node.getProperty(InstallationModel.city));
    }

    @Override
    public String getAddress() {
        return String.valueOf(node.getProperty(InstallationModel.address));
    }

    @Override
    public String getEprtrId() {
        return String.valueOf(node.getProperty(InstallationModel.eprtrId));
    }

    @Override
    public String getPermitId() {
        return String.valueOf(node.getProperty(InstallationModel.permitId));
    }

    @Override
    public String getPermitEntryDate() {
        return String.valueOf(node.getProperty(InstallationModel.permitEntryDate));
    }

    @Override
    public String getPermitExpiryOrRevocationDate() {
        return String.valueOf(node.getProperty(InstallationModel.permitExpiryOrRevocationDate));
    }

    @Override
    public String getLatitude() {
        return String.valueOf(node.getProperty(InstallationModel.latitude));
    }

    @Override
    public String getLongitude() {
        return String.valueOf(node.getProperty(InstallationModel.longitude));
    }

    @Override
    public Sector getSector() {
        return new Sector(node.getSingleRelationship(new InstallationSector(null), Direction.OUTGOING).getEndNode());
    }

    @Override
    public void setCity(String city) {
        node.setProperty(InstallationModel.city, city);
    }

    @Override
    public String getPostCode() {
        return String.valueOf(node.getProperty(InstallationModel.postCode));
    }

    @Override
    public boolean getOpen() {
        return Boolean.valueOf(String.valueOf(node.getProperty(InstallationModel.open)));
    }

    @Override
    public void setPostCode(String postCode) {
        node.setProperty(InstallationModel.postCode, postCode);
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
    public void setOffsetEntitlementForPeriod(Period period, double value, String type){
        OffsetEntitlement offsetEntitlement = new OffsetEntitlement(node.createRelationshipTo(period.node, new OffsetEntitlement(null)));
        offsetEntitlement.setValue(value);
        offsetEntitlement.setType(type);
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
    public void setAllowancesInAllocationForPeriod(Period period, double value) {
        AllowancesInAllocation allowancesInAllocation = new AllowancesInAllocation(node.createRelationshipTo(period.node, new AllowancesInAllocation(null)));
        allowancesInAllocation.setValue(value);
    }

    @Override
    public String name() {
        return LABEL;
    }
}
