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

We retrieve the entries from *db_servers* which has all the jdbc parameters and the site it is associated with and then create a datasource for each entry


