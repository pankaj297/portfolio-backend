# Portfolio Backend API

A RESTful backend API for a personal portfolio website built using **Spring Boot, Spring Data JPA, PostgreSQL, and Cloudinary**.

This backend provides APIs to manage portfolio content such as profile information, skills, projects, experience, education, certifications, achievements, services, and resume data.

---

## 🏗️ Architecture

```text
                         Portfolio Backend
                                │
              ┌─────────────────┼─────────────────┐
              │                 │                 │
              ▼                 ▼                 ▼
          API Layer       Business Layer      Data Layer
              │                 │                 │
        Controllers          Services        Repositories
              │                 │                 │
             DTOs        Business Logic       JPA / Hibernate
              │                 │                 │
        Bean Validation   Cloudinary Logic    PostgreSQL
              │                 │
              └────────┬────────┘
                       ▼
              Global Exception Handling
```

### Additional Integrations

```text
                    Portfolio Backend
                           │
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼
   PostgreSQL          Cloudinary          Frontend
        │                  │                  │
   JPA / Hibernate    Image/File Upload    REST API
                           │
                       CORS Config
                           │
                  Environment Variables
                           │
                     Postman Testing
```

---

## 🚀 Features

- RESTful API architecture
- Spring Boot backend
- PostgreSQL database
- Spring Data JPA / Hibernate
- Layered architecture
- DTO-based request and response handling
- Bean Validation
- Global exception handling
- Custom exception classes
- Cloudinary integration for image and file uploads
- Profile image upload
- Resume PDF upload
- Skill icon upload
- CRUD operations
- Partial update support
- Unique email validation
- File upload validation
- JPA entity relationships
- ModelMapper for DTO mapping
- CORS configuration
- Environment variable support
- Postman API testing

---

## 🛠️ Technologies Used

| Technology          | Purpose                     |
| ------------------- | --------------------------- |
| **Java**            | Backend programming         |
| **Spring Boot**     | Backend framework           |
| **Spring Data JPA** | Database access             |
| **Hibernate**       | ORM and JPA implementation  |
| **PostgreSQL**      | Relational database         |
| **Cloudinary**      | Image and file storage      |
| **Maven**           | Dependency management       |
| **Lombok**          | Reduce boilerplate code     |
| **Bean Validation** | Request validation          |
| **ModelMapper**     | DTO and Entity mapping      |
| **REST API**        | Client-server communication |
| **Postman**         | API testing                 |

---

## 📁 Project Structure

```text
backend/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── myportfolio/
│       │           └── backend/
│       │               │
│       │               ├── config/
│       │               │   ├── CloudinaryConfig.java
|       |               |   |── ModelMapperConfig.java
│       │               │   └── CorsConfig.java
│       │               │
│       │               ├── controller/
│       │               │   ├── MyProfileController.java
│       │               │   ├── MySkillsController.java
│       │               │   ├── ProjectsController.java
│       │               │   ├── ExperienceController.java
│       │               │   ├── EducationController.java
│       │               │   ├── CertificationsController.java
│       │               │   ├── AchievementsController.java
│       │               │   ├── ServicesController.java
│       │               │   ├── ResumeController.java
│       │               │   └── ...
│       │               │
│       │               ├── dto/
│       │               │   ├── request/
│       │               │   └── response/
│       │               │
│       │               ├── exception/
│       │               │   ├── ResourceNotFoundException.java
│       │               │   ├── DuplicateResourceException.java
│       │               │   ├── BadRequestException.java
│       │               │   ├── FileUploadException.java
│       │               │   ├── ErrorResponse.java
│       │               │   └── GlobalExceptionHandler.java
│       │               │
│       │               ├── model/
│       │               │   ├── MyProfile.java
│       │               │   ├── MySkill.java
│       │               │   ├── Project.java
│       │               │   ├── Experience.java
│       │               │   ├── Education.java
│       │               │   ├── Certification.java
│       │               │   ├── Achievement.java
│       │               │   ├── Service.java
│       │               │   └── Resume.java
│       │               │
│       │               ├── repository/
│       │               │
│       │               ├── service/
│       │               │
│       │               └── serviceImpl/
│       │
│       └── resources/
│           └── application.properties
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## 🔄 Request Flow

```text
Frontend / Client
       │
       ▼
   Controller
       │
       ▼
      DTO
       │
       ▼
  Validation
       │
       ▼
    Service
       │
       ├──────────────► Cloudinary
       │
       ▼
   Repository
       │
       ▼
  PostgreSQL
       │
       ▼
    Response
       │
       ▼
Frontend / Client
```

---

## 🗄️ Database & JPA

The application uses **PostgreSQL** as the relational database and **Spring Data JPA / Hibernate** for persistence.

JPA relationships are used to establish relationships between portfolio entities where required.

```text
Entity
  │
  ▼
JPA / Hibernate
  │
  ▼
Repository
  │
  ▼
PostgreSQL
```

---

## ☁️ Cloudinary Integration

Cloudinary is used for storing and managing uploaded media files.

### Supported Uploads

- Profile images
- Skill icons
- Project images
- Company / institution logos
- Certification images
- Resume PDF
- Other portfolio-related files

The application stores the required Cloudinary information such as:

```text
secure_url
public_id
```

The `public_id` can also be used when deleting or replacing files from Cloudinary.

---

## 🛡️ Exception Handling

The application uses centralized exception handling through:

```text
GlobalExceptionHandler
        │
        ├── ResourceNotFoundException
        ├── DuplicateResourceException
        ├── BadRequestException
        ├── FileUploadException
        └── Validation Errors
```

This provides consistent error responses to API clients.

### Example Error Response

```json
{
  "timestamp": "2026-10-04T22:00:00",
  "status": 404,
  "message": "Profile not found",
  "path": "/api/profile/10"
}
```

---

## ✅ Validation

Bean Validation is used to validate incoming API requests.

Examples include:

- Required fields
- Email format validation
- Unique email validation
- String length validation
- File type validation
- File size validation
- Required image/file validation

---

## 🔐 Configuration & Environment Variables

Sensitive configuration values such as database credentials and Cloudinary credentials should not be hardcoded.

Example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
cloudinary.api-key=${CLOUDINARY_API_KEY}
cloudinary.api-secret=${CLOUDINARY_API_SECRET}
```

Sensitive files and credentials should be excluded using `.gitignore`.

---

## 🌐 CORS Configuration

CORS is configured to allow the frontend application to communicate with the Spring Boot backend.

```text
Frontend
   │
   │ HTTP Request
   ▼
Spring Boot REST API
   │
   ▼
CORS Configuration
   │
   ▼
API Response
```

---

## 🧪 API Testing

The APIs are tested using **Postman**.

Testing includes:

- GET requests
- POST requests
- PUT requests
- PATCH requests
- DELETE requests
- Multipart file uploads
- Validation errors
- Exception handling
- CRUD operations

---

## 📌 Main API Modules

The backend contains APIs for managing:

```text
My Profile
   │
   ├── Skills
   ├── Projects
   ├── Experience
   ├── Education
   ├── Certifications
   ├── Achievements
   ├── Services
   ├── Resume
   └── Other Portfolio Content
```

---

## 🏛️ Layered Architecture

The project follows a clean layered architecture:

```text
Controller
    │
    ▼
Service Interface
    │
    ▼
Service Implementation
    │
    ▼
Repository
    │
    ▼
PostgreSQL
```

### Responsibilities

**Controller**

- Handles HTTP requests
- Maps API endpoints
- Returns HTTP responses

**DTO**

- Transfers data between client and backend
- Prevents direct exposure of entity objects
- Handles request validation

**Service**

- Contains business logic
- Coordinates database and Cloudinary operations

**Service Implementation**

- Implements service interfaces
- Handles actual business operations

**Repository**

- Handles database operations using Spring Data JPA

**Model**

- Represents database entities
- Defines JPA relationships

**Exception**

- Handles application and validation errors globally

**Config**

- Contains application configuration such as Cloudinary and CORS

---

## 🎯 Project Goals

The main goals of this project are:

- Build a scalable portfolio backend
- Practice Spring Boot and REST API development
- Implement real-world CRUD operations
- Work with PostgreSQL and JPA relationships
- Implement file and image uploads using Cloudinary
- Apply DTOs and validation
- Implement centralized exception handling
- Follow clean and maintainable backend architecture
- Create a backend that can be integrated with a React frontend

---

## 👨‍💻 Author

**Pankaj Naik**

Full Stack Java Developer

### Technologies

```text
Java
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL
REST API
Cloudinary
React
JavaScript
```
