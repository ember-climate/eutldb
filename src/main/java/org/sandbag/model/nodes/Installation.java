package org.sandbag.model.nodes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.interfaces.InstallationModel;
import org.sandbag.model.relationships.*;
import org.sandbag.model.relationships.installations.*;

import java.util.Iterator;

/**
 * Created by pablo on 14/02/16.
 */
public class Installation implements InstallationModel {

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
    public double getOffsetEntitlementForPeriod(Period period){
        Relationship rel = node.getSingleRelationship(new OffsetEntitlement((null)),Direction.OUTGOING);
        if(rel != null){
            OffsetEntitlement offsetEntitlement = new OffsetEntitlement(rel);
            return offsetEntitlement.getValue();
        }else{
            return -1;
        }
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

    public Iterator<Relationship> getOffsets(){
        return node.getRelationships(new Offsets(null),Direction.OUTGOING).iterator();
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
    public void setPostCode(String postCode) {
        node.setProperty(InstallationModel.postCode, postCode);
    }


    @Override
    public void setVerifiedEmissionsForPeriod(Period period, double value){
        VerifiedEmissions verifiedEmissions = new VerifiedEmissions(node.createRelationshipTo(period.node, new VerifiedEmissions(null)));
        verifiedEmissions.setValue(value);
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

    public VerifiedEmissions getVerifiedEmissionsForPeriod(Period period){
        VerifiedEmissions emissions = null;
        Iterator<Relationship> iterator = node.getRelationships(new VerifiedEmissions((null)),Direction.OUTGOING).iterator();
        boolean emissionsFound = false;
        while(iterator.hasNext() && !emissionsFound){
            VerifiedEmissions tempEmissions = new VerifiedEmissions(iterator.next());
            if(period.getName().equals(tempEmissions.getPeriod().getName())){
                emissions = tempEmissions;
                emissionsFound = true;
            }
        }
        return emissions;
    }

    public AllowancesInAllocation getAllowancesInAllocationForPeriod(Period period){
        AllowancesInAllocation allowances = null;

        Iterator<Relationship> iterator = node.getRelationships(new AllowancesInAllocation((null)),Direction.OUTGOING).iterator();
        boolean allowancesFound = false;

        while(iterator.hasNext() && !allowancesFound){
            AllowancesInAllocation tempAllowances = new AllowancesInAllocation(iterator.next());
            if(period.getName().equals(tempAllowances.getPeriod().getName())){
                allowances = tempAllowances;
                allowancesFound = true;
            }
        }
        return allowances;
    }

    public AllowancesInAllocation getAllowancesInAllocationForPeriodAndType(Period period, String type){
        AllowancesInAllocation allowances = null;
        Iterator<Relationship> iterator = node.getRelationships(new AllowancesInAllocation((null)),Direction.OUTGOING).iterator();
        boolean allowancesFound = false;
        while(iterator.hasNext() && !allowancesFound){
            AllowancesInAllocation tempAllowances = new AllowancesInAllocation(iterator.next());
            if(period.getName().equals(tempAllowances.getPeriod().getName()) &&
                    tempAllowances.getType().equals(type)){
                allowances = tempAllowances;
                allowancesFound = true;
            }
        }
        return allowances;
    }

    public SurrenderedUnits getSurrenderedUnitsForPeriod(Period period){
        SurrenderedUnits surrenderedUnits = null;
        Iterator<Relationship> iterator = node.getRelationships(new SurrenderedUnits((null)),Direction.OUTGOING).iterator();
        boolean surrenderedUnitsFound = false;
        while(iterator.hasNext() && !surrenderedUnitsFound){
            SurrenderedUnits tempSurrenderedUnits = new SurrenderedUnits(iterator.next());
            if(period.getName().equals(tempSurrenderedUnits.getPeriod().getName())){
                surrenderedUnits = tempSurrenderedUnits;
                surrenderedUnitsFound = true;
            }
        }
        return surrenderedUnits;
    }

    public Compliance getComplianceForPeriod(Period period){
        Compliance compliance = null;
        Iterator<Relationship> iterator = node.getRelationships(new Compliance((null)),Direction.OUTGOING).iterator();
        boolean complianceFound = false;
        while(iterator.hasNext() && !complianceFound){
            Compliance tempCompliance = new Compliance(iterator.next());
            if(period.getName().equals(tempCompliance.getPeriod().getName())){
                compliance = tempCompliance;
                complianceFound = true;
            }
        }
        return compliance;
    }
}
