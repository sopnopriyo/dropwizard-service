# Introduction

This is a restful web service build using DropWizard Framework.

# Running The Application

* To test the example application run the following commands.

`mvn clean test`

* To create the example, package the application using [Apache Maven](https://maven.apache.org/) from the root dropwizard directory.

        cd dropwizard
        ./mvnw package
        cd dropwizard-example

* To setup the h2 database run.

        java -jar target/dropwizard-example-$DW_VERSION.jar db migrate example.yml

* To run the server run.

        java -jar target/dropwizard-example-$DW_VERSION.jar server example.yml
