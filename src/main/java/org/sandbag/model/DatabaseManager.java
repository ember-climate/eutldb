package org.sandbag.model;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.sandbag.model.nodes.*;

import java.io.File;

/**
 * Created by root on 15/03/16.
 */
public class DatabaseManager {

    private static GraphDatabaseService graphDb;
    private static Schema schema;

    public Label COUNTRY_LABEL = DynamicLabel.label( CountryModel.LABEL );
    public Label COMPANY_LABEL = DynamicLabel.label( CompanyModel.LABEL );
    public Label INSTALLATION_LABEL = DynamicLabel.label( InstallationModel.LABEL );
    public Label SECTOR_LABEL = DynamicLabel.label( SectorModel.LABEL );
    public Label PERIOD_LABEL = DynamicLabel.label( PeriodModel.LABEL );

    public DatabaseManager(String dbFolder){
        initDatabase(dbFolder);
    }

    public Transaction beginTransaction(){
        return graphDb.beginTx();
    }

    private void initDatabase(String dbFolder){
        if(graphDb == null){
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( new File(dbFolder) );

            try {

                try(Transaction tx = graphDb.beginTx()){

                    System.out.println("Creating indices...");

                    schema = graphDb.schema();
                    IndexDefinition installationIdIndex = schema.indexFor(INSTALLATION_LABEL)
                            .on(InstallationModel.id)
                            .create();
                    IndexDefinition countryNameIndex = schema.indexFor(COUNTRY_LABEL)
                            .on(CountryModel.name)
                            .create();
                    IndexDefinition countryIdIndex = schema.indexFor(COUNTRY_LABEL)
                            .on(CountryModel.id)
                            .create();
                    IndexDefinition periodNameIndex = schema.indexFor(PERIOD_LABEL)
                            .on(PeriodModel.name)
                            .create();
                    IndexDefinition sectorNameIndex = schema.indexFor(SECTOR_LABEL)
                            .on(SectorModel.name)
                            .create();
                    IndexDefinition sectorIdIndex = schema.indexFor(SECTOR_LABEL)
                            .on(SectorModel.id)
                            .create();
                    IndexDefinition companyNameIndex = schema.indexFor(COMPANY_LABEL)
                            .on(CompanyModel.name)
                            .create();
                    IndexDefinition companyRegistrationNumberIndex = schema.indexFor(COMPANY_LABEL)
                            .on(CompanyModel.registrationNumber)
                            .create();

                    tx.success();
                    tx.close();

                    System.out.println("Done!");
                }



            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public Installation createInstallation(String id,
                                           String name,
                                           String city,
                                           String postCode,
                                           String address,
                                           String eprtrId,
                                           String permitId,
                                           String permitEntryDate,
                                           String permitExpiryOrRevocationDate,
                                           String latitude,
                                           String longitude,
                                           Country country,
                                           Company company,
                                           Sector sector){

        Node installationNode = graphDb.createNode(DynamicLabel.label(InstallationModel.LABEL));

        Installation installation = new Installation(installationNode);
        installation.setId(id);
        installation.setName(name);
        //installation.setOpen(open.toLowerCase().equals("open"));
        installation.setCity(city);
        installation.setPostCode(postCode);
        installation.setAddress(address);
        installation.setEprtrId(eprtrId);
        installation.setPermitId(permitId);
        installation.setPermitEntryDate(permitEntryDate);
        installation.setPermitExpiryOrRevocationDate(permitExpiryOrRevocationDate);
        installation.setLatitude(latitude);
        installation.setLongitude(longitude);

        if(country != null){
            installation.setCountry(country);
        }
        if(company != null){
            installation.setCompany(company);
        }
        if(sector != null){
            installation.setSector(sector);
        }


        return installation;

    }

    public Country createCountry(String name, String id){
        Node countryNode = graphDb.createNode(COUNTRY_LABEL);

        Country country = new Country(countryNode);
        country.setName(name);
        country.setId(id);

        return country;
    }

    public Period createPeriod(String name){
        Node periodNode = graphDb.createNode(PERIOD_LABEL);

        Period period = new Period(periodNode);
        period.setName(name);

        return period;
    }

    public Sector createSector(String id, String name){
        Node sectorNode = graphDb.createNode(SECTOR_LABEL);

        Sector sector = new Sector(sectorNode);
        sector.setName(name);
        sector.setId(id);

        return sector;
    }

    public Company createCompany(String name,
                                 String registrationNumber,
                                 String postalCode,
                                 String city,
                                 String address,
                                 String status){
        Node companyNode = graphDb.createNode(COMPANY_LABEL);

        Company company = new Company(companyNode);
        company.setName(name);
        company.setAddress(address);
        company.setCity(city);
        company.setPostalCode(postalCode);
        company.setRegistrationNumber(registrationNumber);
        company.setStatus(status);

        return company;
    }

    public Country getCountryByName(String name){
        Country country = null;
        Node countryNode = graphDb.findNode( COUNTRY_LABEL, CountryModel.name, name );
        if(countryNode != null){
            country = new Country(countryNode);
        }
        return country;

    }
    public Country getCountryById(String id){
        Country country = null;
        Node countryNode = graphDb.findNode( COUNTRY_LABEL, CountryModel.id, id );
        if(countryNode != null){
            country = new Country(countryNode);
        }
        return country;
    }
    public Installation getInstallationById(String id){
        Installation installation = null;
        Node installationNode = graphDb.findNode( INSTALLATION_LABEL, InstallationModel.id, id );
        if(installationNode != null){
            installation = new Installation(installationNode);
        }
        return installation;
    }
    public Period getPeriodByName(String name){
        Period period = null;
        Node periodNode = graphDb.findNode( PERIOD_LABEL, PeriodModel.name, name );
        if(periodNode != null){
            period = new Period(periodNode);
        }
        return period;

    }
    public Sector getSectorById(String id){
        Sector sector = null;
        Node sectorNode = graphDb.findNode( SECTOR_LABEL, SectorModel.id, id );
        if(sectorNode != null){
            sector = new Sector(sectorNode);
        }
        return sector;
    }

    public Company getCompanyByRegistrationNumber(String registrationNumber){
        Company company = null;
        Node companyNode = graphDb.findNode( COMPANY_LABEL, CompanyModel.registrationNumber, registrationNumber );
        if(companyNode != null){
            company = new Company(companyNode);
        }
        return company;

    }
}
