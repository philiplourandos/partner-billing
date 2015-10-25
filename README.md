# Introduction

Sideline project to rewrite the existing partner billing application to address some issues:

* OutOfMemory errors. This is a result of all data being loaded into memory
* Unclear program flow - There is alot of asynchronous work happening that does not make the program easy to understand
* No unit tests
* No db schema management
* Datasource are stored in spring configuration making it harder to add new datasources.

The issues mentioned above have been addressed with:

* Spring batch - Will process data retrieved in chunks
* Spring batch breaks up the job into 'steps' thus giving us a clear idea of execute flow
* We are able to start the whole batch process from a unit test using embedded db
* Using flyway db
* We have added an additional table linked to the site table where the datasource parameters are and will create the datasources on startup

# Program flow


## Load datasources

We retrieve the entries from *db_servers* which has all the jdbc parameters and the site it is associated with and then create a datasource for each entry.
We store these values in a queue so that it can be drained when we have consumed all CDR records from the target datasource and move onto the next

## Read from datasource asterisk_cdr and write to common Cdr db

Read entries from the remote asterisk_cdr database and write them into another database that resembles the asterisk_cdr one. This process will continue until
all datasources are completed. A small amount of processing happens:
* If there is no unique id we generate 1 and set it on the Cdr bean
* We calculate the cost of the call by taking hard coded per second cost value and multiple my billingSeconds.

## Retrieve sites

There are only 2 sites 'JHB' and 'CT' but we have a mechanism that caters for more should it arise. Similar to the datasources, we load the sites and place them
in a queue structure which can be drained. We supply the reader with the current site id we are processing.

## Read from common db and write files

We read all the CDR's for the current site and write the records to the output file defined in the sites table. We repeat this till all the sites are done.
