![grafik](https://github.com/Web-based-Application-Systems-FRA-UAS/webappproject23-24-devteam/assets/126953115/48a1b3b5-82f2-4fe7-a0ff-08ba8bd95dd6)[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/-yaTGY9V)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=13201784)
# Web-basierte Anwendungssysteme Projekt 2023/24​

## Project Overview
### Project Name: Event Planner
### Description: 
This project offers a robust event planning platform that allows users to create, manage, and view events, in addition to creating, managing, and viewing user profiles. Users also have the ability to rate the events they've created. Featuring both a web interface and a backend API, the application facilitates efficient management of event data and user information.


## Technology Stack
+ Java/Spring Boot: Used for backend logic and API endpoints
+ HTML/CSS: Used for frontend design
+ Spring Security: Used for authentication and authorization mechanisms
+ Thymeleaf: Used as a template engine for HTML pages


## Setup and Installation
+ Clone the repository to your local machine
+ Install Java and Maven
+ Needed Extensions: Spring Boot Extension Pack, -Dashboard, -Tools 
+ Execute mvn clean install to build the application
+ Start the application with the Spring Boot Dashboard by selecting our 4 applications


## File Structure
* ***eventplaner:*** The API Gateway serves as the central configuration and launch point for the application, responsible for the necessary setup of components and configurations to ensure the application runs smoothly. This includes initializing the application context and starting the embedded server. It facilitates communication between the event service and user service, streamlining operations and enhancing functionality
* ***eventservice:*** Responsible for managing all business logic and data interactions concerning events, this component acts as a vital bridge between the controllers and the database. It ensures that event data is accurately processed, validated, and stored, maintaining the integrity and efficiency of operations within the system
* ***repository:*** As a crucial part of the data access layer, this component interfaces directly with the database, significantly simplifying the processes of data retrieval and manipulation. It enables other parts of the application to interact with the database seamlessly, without requiring knowledge of the underlying data access implementation
* ***userservice:*** Responsible for managing all the business logic and data interactions related to users, this component acts as an essential bridge between the controllers and the database. It ensures that user data is accurately processed, validated, and stored, safeguarding the integrity and efficiency of user-related operations within the system


## Usage and Examples
1. start all four applications

1. **opening localhost:8080/api**
+ The user has direct access to the API controller, where they have control over all the methods available on the frontend. Additionally, navigating to 8080/api forwards to the Swagger UI
3. **opening localhost:8080**
+ Opening the frontpage the user can look at the events or users, the website is under delopment 
+ Explore Event: on the top right corner the user can either go back to the homepage or the Eventmanager. By clicking on the Event ID ths user can look at all the information given, there the user can return back to the event list
+ Event Manager: first option is to add a new Event, there the user needs to provide all the needed information. second option is to delete an existing event using the specific event ID
+ Users: a list of all the users and if there an organizer, clicking on the users name, all their information is shown. On the top right corner is the option to open the user manager
+ first option is to add a new user, there the user needs to provide all the needed information. second option is to delete an existing user using the specific user ID.

**Using the other services**
+ Each of the services listed below is equipped with a Swagger UI, primarily intended for debugging purposes

4. **opening localhost:8081**
+ the user has direct access to the api-controller, the user has the possibility to create, delete an event, recieve the eventID, add / remove a user by it´s ID from a event or all (Removal only with the specific ID), add a rating from a user to a event.
5. **opening localhost:8082**
+ the user has direct access to the api-controller, there the user can create / delete a event and a user. (Deletion only with the specific ID). The user can also recieve the ID for the users and events 
6. **opening localhost:8083**
+ the user has direct access to the api-controller, there users can be created / deleted / updated or the userID can be recieved


## Contribution

contributors: 
+ Mavin-Moris Scholl (MavinScholl) (1439942)
+ Daniel Schor (Daniel-Schor) (1435234)
+ Yannis Körner (ynnskrnr) (1432965)
+ Davide Pedergnana (Davipede99) (1452903)
+ Tristan Buls (tristanbuls) (1440643)
+ Esrom Johannes (unigithubacc) (1457115) 
