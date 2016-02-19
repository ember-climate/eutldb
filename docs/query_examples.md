# Examples of queries

* [Dave's query](#daves-query)
* [Get installations data](#get-installation-data)
* [Get some companies](#get-some-companies)
* [Three-level companies](#three-level-companies)
* [Get some emissions data](#get-some-emissions-data)


## Get installation data

Gets information from the first 10 installations that are found in the database

```
// get installation data
MATCH (i:INSTALLATION)
RETURN i
LIMIT 10
```

## Dave's query

_**What is the surplus-deficit of free allowances to emissions in the cement sector, by country, in 2014?**_

Filters: SectorCategory="Cement and Lime", Period="2014"
Subtype: "EM Verified" (this means verified emissions), "FA Standard" (this means the normal Free Allocation")
RegCtryName

``` sql
//Dave's query
MATCH (c:COUNTRY)<-[:INSTALLATION_COUNTRY]-(i:INSTALLATION)-[:INSTALLATION_SECTOR]->(s:SECTOR{name:'Cement and Lime'}),
(i)-[ve:VERIFIED_EMISSIONS]->(p:PERIOD{name:'2014'}),
(i)-[fa:FREE_ALLOCATION{type:'FA Standard'}]->(p:PERIOD{name:'2014'})
RETURN c.name AS Country, sum(ve.value) AS Verified_Emissions, sum(fa.value) AS Free_Allocations, sum(ve.value) - sum(fa.value) AS Surplus_Deficit
ORDER BY Country
```

![Dave's query result](/docs/images/daves_query_output.png)

## Get some companies

Get a random set of companies _(limited to 10)_

``` sql
// get some companies
MATCH (p:COMPANY)<-[PARENT_COMPANY]-(p2:COMPANY)
RETURN * LIMIT 10
```

## Three-level companies

Find companies with, at least, two levels of subsidiaries

``` sql
// three level companies
MATCH (p1:COMPANY)<-[:PARENT_COMPANY]-(p2:COMPANY)<-[:PARENT_COMPANY]-(p3:COMPANY)
RETURN *
```

## Get some emissions data

Gets randomly a set of verified emissions values  _(limited to ten results)_

``` sql
//get some emissions data
MATCH (i:INSTALLATION)-[:VERIFIED_EMISSIONS]->(p:PERIOD)
RETURN * limit 10
```
