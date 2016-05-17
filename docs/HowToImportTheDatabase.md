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

  * **eutldb-1.0-SNAPSHOT-jar-with-dependencies.jar** _(s3://eutldb/jars/	eutldb-1.0-SNAPSHOT-jar-with-dependencies.jar)_
  
* Configuration files
  
  * **ImportWholeEUTLDB_executions_file_AWS.xml** _(s3://eutldb/conf/ImportWholeEUTLDB_executions_file_AWS.xml)_
  
* Data files

  * Web Scrape files: **web_scrape.tar.gz** _(s3://eutldb/data/web_scrape.tar.gz)_
  * Sandgab Sectors aggregation: **SandbagSectorsAggregation.csv** _(s3://eutldb/data/SandbagSectorsAggregation.csv)_
  * NACE codes file: **installation_nace_rev2_matching_en.tsv** _(s3://eutldb/data/installation_nace_rev2_matching_en.tsv)_
  * Auction data: **AuctionDataFile.csv** _(s3://eutldb/data/AuctionDataFile.csv)_
  * Power Flag data: **PowerFlagData.tsv** _(s3://eutldb/data/PowerFlagData.tsv)_
  
* Neo4j files
  
  * **Neo4j 2.3.3 distribution** _(http://neo4j.com/artifact.php?name=neo4j-community-2.3.3-unix.tar.gz)_

### 4. Extract compressed data files

``` bash
tar -xvf *.tar.gz
```

### 5. Run the following command

``` bash
sudo java -d64 -jar eutldb-1.0-SNAPSHOT-jar-with-dependencies.jar ImportWholeEUTLDB_executions_file.xml
```

Just be aware that the programs that will be executed are those stated in the _**executions.xml**_ file.
The follow example shows the different programs that must be executed in order to import everything into the database:

``` xml
<scheduled_executions>
	<execution>
		<class_full_name>org.sandbag.programs.ImportEUTLData</class_full_name>
		<arguments>
			<argument>/home/pablo/sandbag/EUTL_data/eutldb</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/installations</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/aircraft_operators</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/compliance</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/NER.tsv</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/Article10c.tsv</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/InstallationsEntitlements.tsv</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/AircraftOperatorsEntitlements.tsv</argument>
			<argument>/home/pablo/sandbag/EUTL_data/web_scrape/offsets</argument>
		</arguments>
	</execution>
	<execution>
		<class_full_name>org.sandbag.programs.ImportSandbagSectorsAggregation</class_full_name>
		<arguments>
			<argument>/home/pablo/sandbag/EUTL_data/eutldb</argument>
			<argument>/home/pablo/sandbag/EUTL_data/SandbagSectorsAggregation.csv</argument>
		</arguments>
	</execution>
	<execution>
		<class_full_name>org.sandbag.programs.ImportInstallationsNACECodes</class_full_name>
		<arguments>
			<argument>/home/pablo/sandbag/EUTL_data/eutldb</argument>
			<argument>/home/pablo/sandbag/EUTL_data/installation_nace_rev2_matching_en.tsv</argument>
		</arguments>
	</execution>
	<execution>
		<class_full_name>org.sandbag.programs.FindPowerFlaggedInstallations</class_full_name>
		<arguments>
			<argument>/home/pablo/sandbag/EUTL_data/eutldb</argument>
		</arguments>
	</execution>
	<execution>
		<class_full_name>org.sandbag.programs.ImportOldPowerFlags</class_full_name>
		<arguments>
			<argument>/home/pablo/sandbag/EUTL_data/eutldb</argument>
			<argument>/home/pablo/sandbag/EUTL_data/PowerFlagData.tsv</argument>
		</arguments>
	</execution>
</scheduled_executions>
```





