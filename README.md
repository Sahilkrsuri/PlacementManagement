# Placement Management System

A web-based **Placement Management System** designed to simplify and manage the campus placement process for students and the Training & Placement Cell (TPC).

The system provides a centralized platform for managing students, companies, job drives, applications, announcements, and student eligibility.

## Features

### 👨‍💼 Admin

* Manage students and users
* Add and manage companies
* Create and manage jobs/drives
* Publish announcements
* View and monitor applications
* Filter eligible students
* Manage user roles and access
* Generate reports and statistics

### 🏢 TPC

* Add and manage companies
* Create job/placement drives
* Post announcements
* Filter students based on eligibility criteria such as CGPA
* View students who applied for drives
* Manage eligible students who can access placement opportunities

### 👨‍🎓 Student

* Secure login
* View available companies and placement drives
* View job/drive details
* Apply for eligible jobs
* Track application status
* View placement announcements
* Receive announcements through email

## Technology Stack

### Frontend

* HTML5
* CSS3
* JavaScript

### Backend

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Other Technologies

* Maven
* Git & GitHub
* REST APIs

## System Architecture

The application follows a layered architecture:

```text
┌──────────────────────────────┐
│           USER               │
│      Student / Admin / TPC   │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       PRESENTATION           │
│      HTML / CSS / JavaScript │
└──────────────┬───────────────┘
               │ REST API
               ▼
┌──────────────────────────────┐
│        CONTROLLER            │
│   Handles HTTP Requests      │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│          SERVICE             │
│       Business Logic         │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│         REPOSITORY           │
│     Spring Data JPA          │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│          MySQL               │
│       Database Layer         │
└──────────────────────────────┘
```

Security is handled using **Spring Security, JWT authentication, role-based authorization, and BCrypt password encryption**.

## Project Structure

```text
PlacementManagement/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tint/edu/PlacementManagemebt/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── filter/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       ├── ExceptionHandler/
│   │   │       └── PlacementManagemebtApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── app.js
│   │       │   └── styles.css
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

## Backend Layers

| Layer            | Responsibility                         |
| ---------------- | -------------------------------------- |
| Controller       | Receives REST API requests             |
| Service          | Contains business logic                |
| Repository       | Performs database operations           |
| Entity           | Represents database tables             |
| DTO              | Transfers request/response data        |
| Config           | Application and security configuration |
| Filter           | Validates JWT authentication           |
| ExceptionHandler | Handles application errors             |

## Authentication & Security

The system uses JWT-based authentication.

### Login Flow

```text
Student/Admin/TPC
       │
       ▼
   Login Request
       │
       ▼
 AuthController
       │
       ▼
 Authentication
       │
       ▼
 JWT Token Generated
       │
       ▼
 Token Returned
       │
       ▼
 Frontend
```

For protected requests, the JWT token is sent with the request and validated by the JWT authentication filter.

Passwords are stored using **BCrypt encryption/hashing** rather than plain text.

## Main Modules

### Authentication Module

* User registration
* User login
* JWT token generation
* Authentication
* Role-based authorization

### Company Module

* Add companies
* Update company information
* View company details

### Job/Drive Module

* Create placement drives
* Define job requirements
* Set eligibility criteria
* Manage drive information

### Application Module

* Students apply for jobs
* Applications are stored in the database
* TPC/Admin can view applications
* Application status can be managed

### Announcement Module

* TPC/Admin can publish announcements
* Students can view announcements
* Email notifications can be sent for announcements

## Database

The application uses **MySQL** with **Spring Data JPA and Hibernate** for database interaction.

Major entities include:

* User
* Student
* Admin
* Company
* Job
* Drive
* Application
* Announcement
* Role

Relationships between these entities are managed using JPA relationships and foreign keys.

## Configuration

Database and application configuration is maintained in:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/PlacementHub
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

**Do not commit real database passwords, JWT secrets, or email credentials to GitHub.**

## How to Run Locally

### Prerequisites

Install:

* Java 21
* Maven
* MySQL
* Git

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

### 1. Clone the Repository

```bash
git clone YOUR_REPOSITORY_URL
cd PlacementManagement
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE PlacementHub;
```

Then update the database credentials in:

```text
src/main/resources/application.properties
```

### 3. Build the Project

On Windows:

```bash
mvnw.cmd clean install
```

or:

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvnw.cmd spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

## API Architecture

The frontend communicates with the backend through REST APIs.

```text
Frontend
   │
   │ HTTP Request / JSON
   ▼
Spring Boot REST Controller
   │
   ▼
Service Layer
   │
   ▼
Repository
   │
   ▼
MySQL
   │
   ▼
JSON Response
   │
   ▼
Frontend
```

## Security

Security features implemented in the project include:

* JWT Authentication
* Role-Based Access Control
* Spring Security
* BCrypt Password Hashing
* Protected REST APIs
* Authentication Filter
* CORS Configuration

## Future Enhancements

* ATS-based resume analysis
* Resume upload and management
* Application status tracking
* Student–TPC communication module
* Placement event calendar
* Improved dashboard and analytics
* Drag-and-drop resume upload
* Advanced placement reports

## Project Purpose

The main objective of this project is to provide a **centralized, secure, and efficient placement management platform** that reduces manual work for the TPC/Admin team and makes placement opportunities easier for students to access and manage.

## Contributors

**Placement Management System — Final Year Project**

Developed as part of the B.Tech academic project at:

**Techno International New Town**

## License

This project is developed for academic and educational purposes.
