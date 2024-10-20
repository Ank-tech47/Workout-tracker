# Workout Tracker Application

## Overview
The **Workout Tracker Application** is a backend service for managing and tracking user workouts. 
It allows users to create, update, delete workouts, and generate workout reports over specific time periods.

---

## Prerequisites

Before setting up the project, ensure you have the following tools installed:

- **Java 8+**
- **Maven 3+**
- **MongoDB** (for data storage)

---

## Setup Instructions

### 1. Clone the Repository 
>>git clone https://github.com/your-username/workout-tracker.git
>>cd workout-tracker
### 2. 2. Create MongoDB Database
Ensure MongoDB is installed and running on your system. To create the database:
>> mongo
>> use workout-tracker
### 3.  Update Application Properties
Ensure that the MongoDB connection string in src/main/resources/application.properties is correct:
-> spring.data.mongodb.uri=mongodb://localhost:27017/workout-tracker
### 4.  Build the Project
Use Maven to clean and build the project:
>> mvn clean install
### 5.  Run the Application
Run the Spring Boot application with the following command:
>> mvn spring-boot:run

## API Documentation
The application uses Swagger for API documentation. 
After starting the app, you can access the Swagger UI to explore and test the APIs at:
url
http://localhost:8080/swagger-ui/index.html