package org.sandbag.model;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.sandbag.model.nodes.*;
import org.sandbag.model.nodes.interfaces.*;

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
    public Label AIRCRAFT_OPERATOR_LABEL = DynamicLabel.label( AircraftOperatorModel.LABEL );
    public Label PROJECT_LABEL = DynamicLabel.label( ProjectModel.LABEL );
    public Label OFFSET_LABEL = DynamicLabel.label( OffsetModel.LABEL );

    public static IndexDefinition installationIdIndex = null;
    public static IndexDefinition countryNameIndex = null;
    public static IndexDefinition countryIdIndex = null;
    public static IndexDefinition periodNameIndex = null;
    public static IndexDefinition sectorNameIndex = null;
    public static IndexDefinition sectorIdIndex  = null;
    public static IndexDefinition companyNameIndex = null;
    public static IndexDefinition companyRegistrationNumberIndex = null;

    /**
     * Constructor
     * @param dbFolder
     */
    public DatabaseManager(String dbFolder){
        initDatabase(dbFolder);
    }

    /**
     *
     * @return
     */
    public Transaction beginTransaction(){
        return graphDb.beginTx();
    }

    /**
     * Database initialization
     * @param dbFolder
     */
    private void initDatabase(String dbFolder){
        if(graphDb == null){
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( new File(dbFolder) );

            try {

                try(Transaction tx = graphDb.beginTx()){

                    schema = graphDb.schema();

                    if(!schema.getIndexes().iterator().hasNext()){

                        System.out.println("Creating indices...");

                        installationIdIndex = schema.indexFor(INSTALLATION_LABEL)
                                .on(InstallationModel.id)
                                .create();
                        countryNameIndex = schema.indexFor(COUNTRY_LABEL)
                                .on(CountryModel.name)
                                .create();
                        countryIdIndex = schema.indexFor(COUNTRY_LABEL)
                                .on(CountryModel.id)
                                .create();
                        periodNameIndex = schema.indexFor(PERIOD_LABEL)
                                .on(PeriodModel.name)
                                .create();
                        sectorNameIndex = schema.indexFor(SECTOR_LABEL)
                                .on(SectorModel.name)
                                .create();
                        sectorIdIndex = schema.indexFor(SECTOR_LABEL)
                                .on(SectorModel.id)
                                .create();
                        companyNameIndex = schema.indexFor(COMPANY_LABEL)
                                .on(CompanyModel.name)
                                .create();
                        companyRegistrationNumberIndex = schema.indexFor(COMPANY_LABEL)
                                .on(CompanyModel.registrationNumber)
                                .create();
                    }



                    tx.success();
                    tx.close();

                    System.out.println("Done!");
                }



            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public AircraftOperator createAircraftOperator(String id,
                                                   String name,
                                                   String city,
                                                   String postCode,
                                                   String address,
                                                   String eprtrId,
                                                   String status,
                                                   String uniqueCodeUnderCommissionRegulation,
                                                   String monitoringPlanId,
                                                   String monitoringPlanYearOfApplicability,
                                                   String monitoringPlanYearOfExpiry,
                                                   String icaoDesignator,
                                                   String latitude,
                                                   String longitude,
                                                   Country country,
                                                   Company company,
                                                   Sector sector){

        Node aircraftOperatorNode = graphDb.createNode(AIRCRAFT_OPERATOR_LABEL);

        AircraftOperator aircraftOperator = new AircraftOperator(aircraftOperatorNode);
        aircraftOperator.setId(id);
        aircraftOperator.setName(name);
        aircraftOperator.setStatus(status);
        aircraftOperator.setCity(city);
        aircraftOperator.setPostCode(postCode);
        aircraftOperator.setAddress(address);
        aircraftOperator.setEprtrId(eprtrId);
        aircraftOperator.setUniqueCodeUnderCommissionRegulation(uniqueCodeUnderCommissionRegulation);
        aircraftOperator.setMonitoringPlanId(monitoringPlanId);
        aircraftOperator.setMonitoringPlanFirstYearOfApplicability(monitoringPlanYearOfApplicability);
        aircraftOperator.setMonitoringPlanYearOfExpiry(monitoringPlanYearOfExpiry);
        aircraftOperator.setIcaoDesignator(icaoDesignator);
        aircraftOperator.setLatitude(latitude);
        aircraftOperator.setLongitude(longitude);

        if(country != null){
            aircraftOperator.setCountry(country);
        }
        if(company != null){
            aircraftOperator.setCompany(company);
        }
        if(sector != null){
            aircraftOperator.setSector(sector);
        }

        return aircraftOperator;
    }

    public Offset createOffset(String amountSt,
                               Installation installation,
                               Project project,
                               Period period,
                               Country originatingCountry){

        try{
            Double amount = Double.parseDouble(amountSt);

            Node offsetNode = graphDb.createNode(OFFSET_LABEL);
            Offset offset = new Offset(offsetNode);

            offset.setAmount(amount);
            if(installation != null){
                offset.setInstallation(installation);
            }
            if(project != null){
                offset.setProject(project);
            }
            if(period != null){
                offset.setPeriod(period);
            }
            if(originatingCountry != null){
                offset.setOriginatingCountry(originatingCountry);
            }

            return offset;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    public Offset createOffset(String amountSt,
                               AircraftOperator aircraftOperator,
                               Project project,
                               Period period,
                               Country originatingCountry){

        try{
            Double amount = Double.parseDouble(amountSt);

            Node offsetNode = graphDb.createNode(OFFSET_LABEL);
            Offset offset = new Offset(offsetNode);

            offset.setAmount(amount);
            if(aircraftOperator != null){
                offset.setAircraftOperator(aircraftOperator);
            }
            if(project != null){
                offset.setProject(project);
            }
            if(period != null){
                offset.setPeriod(period);
            }
            if(originatingCountry != null){
                offset.setOriginatingCountry(originatingCountry);
            }

            return offset;

        }catch (Exception e){
            e.printStackTrace();
            return null;
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

        Node installationNode = graphDb.createNode(INSTALLATION_LABEL);

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

    public Project createProject(String id){
        Node projectNode = graphDb.createNode(PROJECT_LABEL);

        Project project = new Project(projectNode);
        project.setId(id);

        return project;
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
    public AircraftOperator getAircraftOperatorById(String id){
        AircraftOperator aircraftOperator = null;
        Node aircraftOperatorNode = graphDb.findNode( AIRCRAFT_OPERATOR_LABEL, AircraftOperatorModel.id, id );
        if(aircraftOperatorNode != null){
            aircraftOperator = new AircraftOperator(aircraftOperatorNode);
        }
        return aircraftOperator;
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
    public Project getProjectById(String id){
        Project project = null;
        Node projectNode = graphDb.findNode( PROJECT_LABEL, ProjectModel.id, id );
        if(projectNode != null){
            project = new Project(projectNode);
        }
        return project;
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
