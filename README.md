# ![logo](logo.png) Taxi-Service ![logo](logo.png)

**Taxi-Service** is a simple web-application that supports authentication, registration and other CRUD operation. After registration as a new driver and authentication you will be able to add or delete cars, manufacturers and drivers or to see all information about already existed ones. Also, you will have ability to assign drivers to certain cars.

## Features
- registration as a driver;
- authentication;
- displaying all manufacturers/cars/drivers;
- creating new manufacturer/car/driver;
- deletion manufacturer/car/driver;
- assignment a driver to a car.

## Technologies
- Java 11;
- MySQL;
- JDBC;
- Servlet API;
- JSP;
- JSTL;
- TomCat;
- Logger.

## Project structure
The web-application has 3-tier architecture:
- DAO;
- Service;
- Controller.

## Setup
To install and setup this project on your PC follow these steps:
- clone the project from GitHub;
- create the required tables for your database using file `resources/init_db.sql`;
- add your DB configurations in `util/ConnectionUtil`;
- run project using TomCat (it is recommended to use version 9.0.50).

## UML diagram
![UML](uml.png)