# Kotlin - REST

## REST service in Kotlin with Spring Boot and MongoDB

This is REST service using Kotlin and Spring Boot. It is retrieving data from MongoDB.
To run this service use Gradle's bootRun taks. You have to have running Mongo data base.
Service use Basic Authentication. User list is in file: resources/user.properties

# REST API

## Get Transactions

* URL

`/api/v1/transaction`

* method

`GET`

* URL params:

`account_type=[string]`

_you can use many id separated by a comma_

_you can use word "ALL" or omit this parameter to get transactions of all account types_ 

`customer_id=[string]`

_you can use many id separated by a comma_

_you can use word "ALL" or omit this parameter to get transactions of all customers_

* Example:

`/api/v1/transaction?account_type=1,4&customer_id=ALL`

---

## v1.2.0
* only one collection in database

## v1.1.1

* bugfixing
* API description
* refactoring

## v1.1.0

* data base reading optimization
* authentication