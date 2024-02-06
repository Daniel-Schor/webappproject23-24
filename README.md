![grafik](https://github.com/Web-based-Application-Systems-FRA-UAS/webappproject23-24-devteam/assets/126953115/48a1b3b5-82f2-4fe7-a0ff-08ba8bd95dd6)[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/-yaTGY9V)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=13201784)
# Web-basierte Anwendungssysteme Projekt 2023/24​

## Project Overview
### Project Name: Event Planner
**Description:** This project is a comprehensive event planning application that enables a user to create, manage, and view events also to create, manage and view users. The User has the option to rate the createt event. It includes a web interface as well as a backend API for managing event data and user information.


**Technology Stack**
+ Java/Spring Boot: Used for backend logic and API endpoints.
+ HTML/CSS: Used for frontend design.
+ Spring Security: Used for authentication and authorization mechanisms.
+ Thymeleaf: Used as a template engine for HTML pages.


**Setup and Installation**
+ Clone the repository to your local machine.
+ Install Java and Maven.
+ Needed Extensions: Spring Boot Extension Pack, -Dashboard, -Tools 
+ Execute mvn clean install to build the application.
+ Start the application with the Spring Boot Dashboard by selecting our 4 applications.


**File Structure**
* ***eventplaner:*** central configuration and launch point for the application using the API Gateway, is responisble for the necessary set up of components and configurations to get the application running, including initializing the application context, and starting the embedded server. communicates with eventservice and userservice.
* ***eventservice:*** responisble for handling all the business logic and data interaction related to events. It serves as a bridge between the controllers and the database, ensuring that event data is correctly processed, validated, and stored.
* ***repository:*** part of data access layer, interfacing directly with the database. Simplifies the process of data retrieval and manipulation, allowing other parts of the application to interact with the database without needing to know the underkyung data access implementation. 
* ***userservice:*** responisble for handling all the business logic and data interaction related to users. It serves as a bridge between the controllers and the database, ensuring that event data is correctly processed, validated, and stored. 


**Usage and Examples**
1. start all four applications
1. opening localhost:8080
+ Opening the frontpage the user can look at the events or users
+ Explore Event: on the top right corner the user can either go back to the homepage or the Eventmanager. By clicking on the Event ID ths user can look at all the information given, there the user can return back to the event list.
+ Event Manager: first option is to add a new Event, there the user needs to provide all the needed information. second option is to delete an existing event using the specific event ID.
+ Users: a list of all the users and if there an organizer, clicking on the users name, all their information is shown. On the top right corner is the option to open the user manager.
+ first option is to add a new user, there the user needs to provide all the needed information. second option is to delete an existing user using the specific user ID.
1. opening localhost:8080/api
+ the user has direct access to the api-controller, there he has controll over all the methodes that are used on the frontend
1. opening localhost:8081
+ the user has direct access to the api-controller, the user has the possibility to create, delete an event, recieve the eventID, add / remove a user by it´s ID from a event or all (Removal only with the specific ID), add a rating from a user to a event.
1. opening localhost:8082
+ the user has direct access to the api-controller, there the user can create / delete a event and a user. (Deletion only with the specific ID). The user can also recieve the ID for the users and events.
1. opening localhost:8083
+ the user has direct access to the api-controller, there users can be created / deleted / updated or the userID can be recieved


**Contribution**
+ contributors: Mavin-Moris Scholl (MavinScholl) (1439942), Daniel Schor (Daniel-Schor) (187), Yannis Körner (ynnskrnr) (1432965), Davide Pedergnana (Davipede99) (1452903), Tristan Buls (tristanbuls) (187), Esrom Johannes (unigithubacc) (187) 
