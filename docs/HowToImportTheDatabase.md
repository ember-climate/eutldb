## How to import the database

Here's a step-by-step guide on how to import the database:

### 1. Launch an AWS instance

  Preferably a **m3.medium** or more powerful instance type 
    
    m3.medium specs: vCPU: 1 	Mem(GiB): 3.75	SSD Storage (GB):	1 x 4 
 

### 2. Install java 8

``` bash
sudo yum remove java-1.7.0-openjdk  (optional)
sudo yum install java-1.8.0
```

### 3. Get the following files

* JAR files

  * **EUTLDBImporter.jar** _(s3://eutldb/jars/eutl-web-scraper.jar)_
  * **ImportInstallationsNACECodes.jar** _(s3://eutldb/jars/ImportInstallationsNACECodes.jar)_
  * **ImportSandbagSectorsAggregation.jar** _(s3://eutldb/jars/ImportSandbagSectorsAggregation.jar)_
  * **ImportOldPowerFlags.jar** _(s3://eutldb/jars/ImportOldPowerFlags.jar)_
  
* Data files

  * Web Scrape files: **web_scrape.tar.gz** _(s3://eutldb/data/web_scrape.tar.gz)_
  * Sandgab Sectors aggregation: **SandbagSectorsAggregation.csv** _(s3://eutldb/data/SandbagSectorsAggregation.csv)_
  * NACE codes file: **installation_nace_rev2_matching_en.tsv** _(s3://eutldb/data/installation_nace_rev2_matching_en.tsv)_
  
* Neo4j files
  
  * **Neo4j 2.3.3 distribution** _(http://neo4j.com/artifact.php?name=neo4j-community-2.3.3-unix.tar.gz)_

### 4. Extract compressed data files

``` bash
tar -xvf *.tar.gz
```

### 5. Run the following set of commands

1. Basic EUTL database

``` bash
sudo java -d64 -classpath ".:EUTLDBImporter.jar:neo4j-community-2.3.3/lib/*" org.sandbag.programs.ImportEUTLData eutldb web_scrape/installations web_scrape/aircraft_operators web_scrape/compliance web_scrape/ner.tsv web_scrape/article10c.tsv web_scrape/InstallationsEntitlements.tsv web_scrape/AircraftOperatorsEntitlements.tsv web_scrape/offsets
```

2. Sandbag Sectors aggregation

``` bash
sudo java -d64 -classpath ".:ImportSandbagSectorsAggregation_jar.jar:neo4j-community-2.3.3/lib/*" org.sandbag.programs.ImportSandbagSectorsAggregation eutldb SandbagSectorsAggregation.csv 
```

3. NACE codes

``` bash
sudo java -d64 -classpath ".:ImportInstallationsNACECodes.jar:neo4j-community-2.3.3/lib/*" org.sandbag.programs.ImportInstallationsNACECodes eutldb_new installation_nace_rev2_matching_en.tsv 
```





