# Examples of queries

## Dave's query

_**What is the surplus-deficit of free allowances to emissions in the cement sector, by country, in 2014?**_

Filters: SectorCategory="Cement and Lime", Period="2014"
Subtype: "EM Verified" (this means verified emissions), "FA Standard" (this means the normal Free Allocation")
RegCtryName

``` cypher
//Dave's query
MATCH (c:COUNTRY)<-[:INSTALLATION_COUNTRY]-(i:INSTALLATION)-[:INSTALLATION_SECTOR]->(s:SECTOR{name:'Cement and Lime'}),
(i)-[ve:VERIFIED_EMISSIONS]->(p:PERIOD{name:'2014'}),
(i)-[fa:FREE_ALLOCATION{type:'FA Standard'}]->(p:PERIOD{name:'2014'})
RETURN c.name AS Country, sum(ve.value) AS Verified_Emissions, sum(fa.value) AS Free_Allocations, sum(ve.value) - sum(fa.value) AS Surplus_Deficit
ORDER BY Country
```
