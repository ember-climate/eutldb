## Programs included

### ImportEUTLData

This program imports all the data obtained after running the EUTL web scraper.
It expects the following set of parameters:

1. Database folder
2. Installations folder
3. Aircraf Operators folder
4. Compliance Data folder
5. NER allocation data file
6. Article 10c data file
7. Installations Offset Entitlements file
8. Aircraft Operators Offset Entitlements file
9. Offsets folder

### ImportSandbagSectorsAggregation

Imports a more meaningful sectors aggregation elaborated by Sandbag's team.
The following set of parameters are expected:

1. Database folder
2. Sandbag Sectors aggregation file (.csv)

### ImportInstallationsNACECodes

Imports NACE codes into the database and their links to the installations provided in the input file.
Parameters:

1. Database folder
2. Installations <-> NACE code file (.tsv)


