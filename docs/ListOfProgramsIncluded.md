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
3. 

### FindPowerFlaggedInstallations

Looks for installations fullfilling at least one of the following two rules:

* Being associated to one of the NACE codes included in this list: _[35.00, 35.10, 35.11, 35.12, 35.13, 35.14, 35.30]_
* Having Article 10c free allocation values associated

All installations complying to either of the aforementioned rules are flagged as **Power**. 

Parameters: 

1. Database folder


### ImportOldPowerFlags

Flags as Power all the installations included in the input CSV file.

Parameters:

1. Database folder
2. Power flag file

