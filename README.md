# Portfolio Backend API

A RESTful backend API for a personal portfolio website built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, PostgreSQL, and Cloudinary**.

This backend provides APIs to manage portfolio content such as profile information, skills, projects, experience, education, certifications, achievements, services, resume, blog, and contact messages.

The application uses **JWT-based authentication and role-based authorization** to protect administrative operations while keeping public portfolio APIs accessible to visitors.

---

## 🏗️ Architecture

```text
                         Portfolio Backend
                                │
          ┌─────────────────────┼─────────────────────┐
          │                     │                     │
          ▼                     ▼                     ▼
      API Layer          Security Layer        Data Layer
          │                     │                     │
    Controllers          Spring Security        Repository
          │                     │                     │
        DTOs              JWT Authentication    JPA/Hibernate
          │                     │                     │
     Validation           Role-based Access     PostgreSQL
          │                     │
          └──────────────┬──────┘
                         ▼
                Business Layer
                         │
                      Service
                         │
                   ServiceImpl
                         │
                         ▼
              Global Exception Handling
```

---

## 🔐 Authentication & Authorization

The application uses **Spring Security with JWT (JSON Web Token)** for authentication and authorization.

There are two types of API access:

### Public APIs

Visitors can access portfolio information without logging in.

```text
GET /api/profile
GET /api/skills
GET /api/projects
GET /api/experience
GET /api/education
GET /api/certifications
GET /api/achievements
GET /api/services
GET /api/resume
GET /api/blog
```

### Admin APIs

Only an authenticated user with the `ADMIN` role can create, update, or delete portfolio content.

```text
POST   /api/**
PUT    /api/**
PATCH  /api/**
DELETE /api/**
```

The admin must send the JWT token in the request header:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## 👤 Admin Authentication Flow

```text
Admin
  │
  ▼
POST /api/auth/login
  │
  ▼
Username + Password
  │
  ▼
Spring Security AuthenticationManager
  │
  ▼
UserDetailsService
  │
  ▼
PostgreSQL
  │
  ▼
Password Verification using BCrypt
  │
  ▼
JWT Token Generated
  │
  ▼
Admin receives JWT
  │
  ▼
Authorization: Bearer <JWT>
  │
  ▼
JwtAuthenticationFilter
  │
  ▼
JWT Validation
  │
  ▼
ROLE_ADMIN
  │
  ▼
Protected API Access
```

---

## 📩 Contact API Security

The contact form has special access rules.

Visitors should be able to send messages without logging in, but contact messages must only be visible and deletable by the admin.

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/contact` | 🌐 Public |
| GET | `/api/contact` | 🔐 ADMIN |
| GET | `/api/contact/{id}` | 🔐 ADMIN |
| DELETE | `/api/contact/{id}` | 🔐 ADMIN |

Example:

```text
Visitor
   │
   ▼
POST /api/contact
   │
   ▼
Public API
   │
   ▼
PostgreSQL
```

Admin:

```text
Admin
   │
   ▼
Login
   │
   ▼
JWT Token
   │
   ▼
GET /api/contact
DELETE /api/contact/{id}
   │
   ▼
ADMIN Authorization
```

This prevents public users from reading private contact messages.

---

## 🚀 Features

- RESTful API architecture
- Spring Boot
- Spring Security
- JWT authentication
- Role-based authorization
- Admin authentication
- BCrypt password encryption
- PostgreSQL database
- Spring Data JPA / Hibernate
- Layered architecture
- DTO-based request and response handling
- Bean Validation
- Global exception handling
- Custom exception classes
- Cloudinary integration
- Profile image upload
- Resume PDF upload
- Skill icon upload
- Project image upload
- Certification image upload
- Company/institution logo upload
- CRUD operations
- Partial update support
- Unique email validation
- File type validation
- File size validation
- JPA entity relationships
- ModelMapper
- CORS configuration
- Environment variable support
- Postman API testing

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| **Java** | Backend programming |
| **Spring Boot** | Backend framework |
| **Spring Security** | Authentication and authorization |
| **JWT** | Token-based authentication |
| **Spring Data JPA** | Database access |
| **Hibernate** | ORM and JPA implementation |
| **PostgreSQL** | Relational database |
| **Cloudinary** | Image and file storage |
| **Maven** | Dependency management |
| **Lombok** | Reduce boilerplate code |
| **Bean Validation** | Request validation |
| **ModelMapper** | DTO and Entity mapping |
| **REST API** | Client-server communication |
| **Postman** | API testing |

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
│       │
│       │               ├── config/
│       │               │   ├── CloudinaryConfig.java
│       │               │   ├── ModelMapperConfig.java
│       │               │   ├── CorsConfig.java
│       │               │   ├── SecurityConfig.java
│       │               │   └── AdminInitializer.java
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
│       │               │   ├── BlogController.java
│       │               │   ├── ContactController.java
│       │               │   └── AuthController.java
│       │               │
│       │               ├── dto/
│       │               │   ├── LoginRequestDto.java
│       │               │   ├── LoginResponseDto.java
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
│       │               │   ├── Resume.java
│       │               │   ├── User.java
│       │               │   └── Role.java
│       │               │
│       │               ├── repository/
│       │               │   ├── ...
│       │               │   └── UserRepository.java
│       │               │
│       │               ├── security/
│       │               │   ├── JwtService.java
│       │               │   ├── JwtAuthenticationFilter.java
│       │               │   └── CustomUserDetailsService.java
│       │               │
│       │               ├── services/
│       │               │   └── AuthService.java
│       │               │
│       │               └── servicesImpl/
│       │                   └── AuthServiceImpl.java
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

### Public Request

```text
Frontend
   │
   ▼
GET Request
   │
   ▼
Spring Security
   │
   ▼
Public Access
   │
   ▼
Controller
   │
   ▼
DTO
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
   │
   ▼
Response
```

### Admin Request

```text
Frontend / Admin Dashboard
          │
          ▼
Authorization: Bearer <JWT>
          │
          ▼
JwtAuthenticationFilter
          │
          ▼
JWT Validation
          │
          ▼
ROLE_ADMIN
          │
          ▼
Controller
          │
          ▼
Service
          │
          ▼
Repository
          │
          ▼
PostgreSQL
```

---

## 🔑 Authentication API

### Login

```http
POST /api/auth/login
```

Request:

```json
{
    "username": "admin",
    "password": "your-password"
}
```

Response:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "username": "admin",
    "role": "ADMIN"
}
```

For protected APIs, send:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 🛡️ Security Components

### `SecurityConfig.java`

Configures:

- Spring Security
- Stateless JWT authentication
- Public and protected endpoints
- ADMIN role authorization
- JWT filter
- BCrypt password encoder
- AuthenticationManager

### `JwtService.java`

Responsible for:

- Generating JWT tokens
- Extracting username
- Validating token
- Checking token expiration

### `JwtAuthenticationFilter.java`

Responsible for:

- Reading the `Authorization` header
- Extracting the Bearer token
- Validating JWT
- Setting authentication in Spring Security context

### `CustomUserDetailsService.java`

Loads the admin user from PostgreSQL and provides user details to Spring Security.

### `AdminInitializer.java`

Creates the initial ADMIN user when the application starts if the admin user does not already exist.

---

## 🗄️ Database & JPA

The application uses **PostgreSQL** as the relational database and **Spring Data JPA / Hibernate** for persistence.

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

### Authentication Tables

The application contains a `users` table for admin authentication.

```text
users
--------------------------------
id
username
password
role
enabled
```

Passwords are stored using **BCrypt hashing** instead of plain text.

---

## ☁️ Cloudinary Integration

Cloudinary is used for storing and managing uploaded media files.

### Supported Uploads

- Profile images
- Skill icons
- Project images
- Company/institution logos
- Certification images
- Resume PDF
- Other portfolio-related files

The application stores:

```text
secure_url
public_id
```

The `public_id` can also be used when deleting or replacing files from Cloudinary.

---

## 🔐 Configuration & Environment Variables

Sensitive configuration values should not be hardcoded.

Example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
cloudinary.api-key=${CLOUDINARY_API_KEY}
cloudinary.api-secret=${CLOUDINARY_API_SECRET}

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000

admin.username=${ADMIN_USERNAME}
admin.password=${ADMIN_PASSWORD}
```

Sensitive files and credentials should be excluded using `.gitignore`.

Never commit:

```text
DB_PASSWORD
JWT_SECRET
ADMIN_PASSWORD
CLOUDINARY_API_SECRET
```

to GitHub.

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

- Login
- JWT authentication
- Public GET requests
- Protected POST requests
- Protected PUT requests
- Protected PATCH requests
- Protected DELETE requests
- Contact form submission
- Admin contact retrieval
- Admin contact deletion
- Multipart file uploads
- Validation errors
- Exception handling
- CRUD operations
- Invalid JWT testing
- Expired JWT testing

---

## 🔒 Security Testing

### Public GET

```http
GET /api/projects
```

Expected:

```text
200 OK
```

No JWT required.

### Public Contact POST

```http
POST /api/contact
```

Expected:

```text
200 OK / 201 Created
```

No JWT required.

### Admin Contact GET

```http
GET /api/contact
```

Without JWT:

```text
401 / 403
```

With valid ADMIN JWT:

```text
200 OK
```

### Admin Contact DELETE

```http
DELETE /api/contact/1
```

Without JWT:

```text
401 / 403
```

With valid ADMIN JWT:

```text
200 OK
```

### Protected POST

```http
POST /api/projects
```

Without JWT:

```text
401 / 403
```

With valid ADMIN JWT:

```text
200 OK / 201 Created
```

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
   ├── Blog
   └── Contact
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

**Security**

- Handles authentication
- Validates JWT
- Provides role-based authorization

**Exception**

- Handles application and validation errors globally

**Config**

- Contains application configuration such as Security, Cloudinary, CORS, and ModelMapper

---

## 🎯 Project Goals

The main goals of this project are:

- Build a scalable portfolio backend
- Practice Spring Boot and REST API development
- Implement real-world CRUD operations
- Implement JWT-based authentication
- Implement Spring Security
- Implement role-based authorization
- Secure administrative APIs
- Work with PostgreSQL and JPA relationships
- Implement file and image uploads using Cloudinary
- Apply DTOs and validation
- Implement centralized exception handling
- Follow clean and maintainable backend architecture
- Create a backend that can be integrated with a React frontend
- Provide public portfolio APIs and a secure admin management system

---

## 👨‍💻 Author

**Pankaj Naik**

Full Stack Java Developer

### Technologies

```text
Java
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
PostgreSQL
REST API
Cloudinary
React
JavaScript
```
