# Portfolio Backend API

A RESTful backend API for a personal portfolio website built using Spring Boot, Spring Data JPA, PostgreSQL and Cloudinary.

This backend provides APIs to manage profile information, skills, projects, experience, education, certifications, achievements, services, resume and other portfolio content.

---

## 🚀 Features

- RESTful API architecture
- Spring Boot backend
- PostgreSQL database
- Spring Data JPA / Hibernate
- DTO-based request and response handling
- Bean Validation
- Global Exception Handling
- Cloudinary integration for image and file uploads
- Profile image upload
- Resume PDF upload
- Skill icon upload
- CRUD operations
- Unique email validation
- File upload validation
- CORS configuration
- Partial update support
- Clean layered architecture

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Cloudinary | Image and file storage |
| Maven | Dependency management |
| Lombok | Reduce boilerplate code |
| Bean Validation | Request validation |
| ModelMapper | DTO mapping |
| REST API | Client-server communication |

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
│       │               │
│       │               ├── repository/
│       │               │
│       │               ├── services/
│       │               │
│       │               └── servicesImpl/
│       │
│       └── resources/
│           └── application.properties
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
