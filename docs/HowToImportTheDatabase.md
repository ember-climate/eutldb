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

```






