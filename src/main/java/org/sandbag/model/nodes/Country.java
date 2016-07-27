package org.sandbag.model.nodes;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.interfaces.CountryModel;
import org.sandbag.model.relationships.*;
import org.sandbag.model.relationships.aircraft_ops.AircraftOperatorCountry;
import org.sandbag.model.relationships.installations.InstallationCountry;

import java.util.Iterator;

/**
 * Created by pablo on 14/02/16.
 */
public class Country implements CountryModel {

    Node node = null;

    public Country(Node node){
        this.node = node;
    }

    public String getId() {
        return String.valueOf(node.getProperty(CountryModel.id));
    }

    public void setId(String id) {
        node.setProperty(CountryModel.id, id);

    }

    public String getName() {
        return String.valueOf(node.getProperty(CountryModel.name));
    }

    @Override
    public double getCenterLatitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.centerLatitude)));
    }

    @Override
    public double getCenterLongitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.centerLongitude)));
    }

    @Override
    public double getBoundingBoxMaxLatitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.boundingBoxMaxLatitude)));
    }

    @Override
    public double getBoundingBoxMinLatitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.boundingBoxMinLatitude)));
    }

    @Override
    public double getBoundingBoxMaxLongitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.boundingBoxMaxLongitude)));
    }

    @Override
    public double getBoundingBoxMinLongitude() {
        return Double.parseDouble(String.valueOf(node.getProperty(CountryModel.boundingBoxMinLongitude)));
    }

    public void setName(String value) {
        node.setProperty(CountryModel.name, value);
    }

    @Override
    public void setCenterLatitude(double value) {
        node.setProperty(CountryModel.centerLatitude, value);
    }

    @Override
    public void setCenterLongitude(double value) {
        node.setProperty(CountryModel.centerLongitude, value);
    }

    @Override
    public void setBoundingBoxMaxLatitude(double value) {
        node.setProperty(CountryModel.boundingBoxMaxLatitude, value);
    }

    @Override
    public void setBoundingBoxMaxLongitude(double value) {
        node.setProperty(CountryModel.boundingBoxMaxLongitude, value);
    }

    @Override
    public void setBoundingBoxMinLatitude(double value) {
        node.setProperty(CountryModel.boundingBoxMinLatitude, value);
    }

    @Override
    public void setBoundingBoxMinLongitude(double value) {
        node.setProperty(CountryModel.boundingBoxMinLongitude, value);
    }

    @Override
    public Iterator<Relationship> getInstallationCountry() {
        return node.getRelationships(new InstallationCountry(null)).iterator();
    }

    @Override
    public Iterator<Relationship> getAircraftOperatorCountry() {
        return node.getRelationships(new AircraftOperatorCountry(null)).iterator();
    }

    @Override
    public String name() {
        return LABEL;
    }

    @Override
    public void setAuctionedForPeriod(Period period, double value, String source, String type){
        Auctioned auctioned = new Auctioned(node.createRelationshipTo(period.node, new Auctioned(null)));
        auctioned.setAmount(value);
        auctioned.setSource(source);
        auctioned.setType(type);
    }

    public void setOffsets2013Onwards(Offset offset, String type, String reference){
        Offsets2013Onwards rel = new Offsets2013Onwards(node.createRelationshipTo(offset.node, new Offsets2013Onwards(null)));
        rel.setType(type);
        rel.setReference(reference);
    }

    public void setLegalCap(Period period, double amount, String source){
        LegalCap legalCap = new LegalCap(node.createRelationshipTo(period.node, new LegalCap(null)));
        legalCap.setAmount(amount);
        legalCap.setSource(source);
    }

    public void setVerifiedEmissionsEUWide(Period period, double value, String type){
        VerifiedEmissionsEUWide verifiedEmissionsEUWide = new VerifiedEmissionsEUWide(node.createRelationshipTo(period.node, new VerifiedEmissionsEUWide(null)));
        verifiedEmissionsEUWide.setValue(value);
        verifiedEmissionsEUWide.setType(type);
    }


    public void setAllowancesInAllocationEUWide(Period period, double value, String type) {
        AllowancesInAllocationEUWide allowancesInAllocationEUWide = new AllowancesInAllocationEUWide(node.createRelationshipTo(period.node, new AllowancesInAllocationEUWide(null)));
        allowancesInAllocationEUWide.setValue(value);
        allowancesInAllocationEUWide.setType(type);
    }

    public void setOffsetsEUWide(Period period, double value, String type){
        OffsetsEUWide offsetsEUWide = new OffsetsEUWide(node.createRelationshipTo(period.node, new OffsetsEUWide(null)));
        offsetsEUWide.setValue(value);
        offsetsEUWide.setType(type);
    }

    public void setLegalCapEUWide(Period period, double value, String type){
        LegalCapEUWide legalCapEUWide = new LegalCapEUWide(node.createRelationshipTo(period.node, new LegalCapEUWide(null)));
        legalCapEUWide.setValue(value);
        legalCapEUWide.setType(type);
    }

    public void setOffsetEntitlementsEUWide(Period period, double value, String type){
        OffsetEntitlementEUWide offsetEntitlementEUWide = new OffsetEntitlementEUWide(node.createRelationshipTo(period.node, new OffsetEntitlementEUWide(null)));
        offsetEntitlementEUWide.setValue(value);
        offsetEntitlementEUWide.setType(type);
    }

    public void setAuctionedEUWide(Period period, double value, String type){
        AuctionedEUWide auctionedEUWide = new AuctionedEUWide(node.createRelationshipTo(period.node, new AuctionedEUWide(null)));
        auctionedEUWide.setValue(value);
        auctionedEUWide.setType(type);
    }
}
