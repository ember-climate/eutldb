package org.sandbag.model;

import org.neo4j.cypher.internal.compiler.v1_9.commands.expressions.Count;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

import java.io.File;

/**
 * Created by root on 15/03/16.
 */
public class DatabaseManager {

    private static GraphDatabaseService graphDb;
    private static Schema schema;

    public DatabaseManager(String dbFolder){
        initDatabase(dbFolder);
    }

    private void initDatabase(String dbFolder){
        if(graphDb == null){
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( new File(dbFolder) );

            try {

                try(Transaction tx = graphDb.beginTx()){

                    System.out.println("Creating indices...");

                    schema = graphDb.schema();
                    IndexDefinition installationIdIndex = schema.indexFor(DynamicLabel.label(Installation.LABEL))
                            .on(InstallationModel.id)
                            .create();
                    IndexDefinition countryNameIndex = schema.indexFor(DynamicLabel.label(Country.LABEL))
                            .on(CountryModel.name)
                            .create();
                    IndexDefinition periodNameIndex = schema.indexFor(DynamicLabel.label(Period.LABEL))
                            .on(PeriodModel.name)
                            .create();
                    IndexDefinition sectorNameIndex = schema.indexFor(DynamicLabel.label(Sector.LABEL))
                            .on(SectorModel.name)
                            .create();
                    IndexDefinition companyNameIndex = schema.indexFor(DynamicLabel.label(Company.LABEL))
                            .on(CompanyModel.name)
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

    private Installation createInstallationNode(String id,
                                        String name,
                                        String city,
                                        String postCode,
                                        String open,
                                        String address,
                                        String eprtrId,
                                        String permitId,
                                        String permitEntryDate,
                                        String permitExpiryOrRevocationDate,
                                        Country country,
                                        Company company){

        Node installationNode = graphDb.createNode(DynamicLabel.label(InstallationModel.LABEL));

        Installation installation = new Installation(installationNode);
        installation.setId(id);
        installation.setName(name);
        installation.setOpen(open.toLowerCase().equals("open"));
        installation.setCity(city);
        installation.setPostCode(postCode);
        installation.setAddress(address);
        installation.setEprtrId(eprtrId);
        installation.setPermitId(permitId);
        installation.setPermitEntryDate(permitEntryDate);
        installation.setPermitExpiryOrRevocationDate(permitExpiryOrRevocationDate);

        installation.setCountry(country);
        installation.setCompany(company);

        return installation;

    }

    private Country createCountry(String name){
        Node countryNode = graphDb.createNode(DynamicLabel.label(CountryModel.LABEL));

        Country country = new Country(countryNode);
        country.setName(name);

        return country;
    }

    private Company createCompany(String name){
        Node companyNode = graphDb.createNode(DynamicLabel.label(CompanyModel.LABEL));

        Company company = new Company(companyNode);
        company.setName(name);

        return company;
    }
}
