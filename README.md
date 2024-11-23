# EduTrack

**Streamline Learning, Empower Success**

EduTrack is an education management system designed to simplify learning processes, offering an intuitive interface for managing courses, assignments, enrollments, submissions, and users. With role-based access control, EduTrack empowers students, instructors, and admins with secure, efficient tools.

## Features

- **Role-Based Access Control**: Secure and differentiated access for `Admin`, `Instructor`, and `Student`.
- **Course Management**: Manage courses from creation to deletion.
- **Assignment Handling**: Full lifecycle support for assignments.
- **Enrollment Management**: Administer enrollments with ease.
- **Submission Tracking**: Monitor assignment submissions for students and instructors.
- **User Management**: Create and manage users (Admin-only).
- **Authentication**: JWT-based secure login.

## Tech Stack

- **Backend**: Spring Boot
- **Authentication**: JWT (JSON Web Token)
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing**: JUnit, Mockito
- **Dependency Injection**: Spring Framework


## Roles and Permissions
- **Admin**:
    - Manage users, courses, assignments, enrollments.
- **Instructor**:
    - Manage courses, assignments, and submissions.
- **Student**:
    - View courses, submit assignments.

## API Endpoints

### Authentication
- **`POST /auth/login`**: Authenticate and retrieve a JWT.

### Assignments
- **`GET /assignments`**: Fetch all assignments (Instructor/Admin only).
- **`GET /assignments/{id}`**: Fetch a specific assignment (Student only).
- **`POST /assignments`**: Create a new assignment (Instructor/Admin only).

### Courses
- **`GET /courses`**: Fetch all courses (Instructor/Admin only).
- **`POST /courses`**: Create a course (Admin only).
- **`PUT /courses/{id}`**: Update a course (Instructor/Admin only).

### Enrollments
- **`GET /enrollments`**: Fetch all enrollments (Admin only).
- **`POST /enrollments`**: Create a new enrollment (Admin only).

### Submissions
- **`GET /submissions`**: Fetch all submissions (Instructor/Student).
- **`POST /submissions`**: Create a submission (Instructor/Student).

### Users
- **`GET /users`**: Fetch all users (Admin only).
- **`POST /users`**: Create a new user (Admin only).


## Project Structure

```
src
├── main
│   ├── java
│   │   └── dev.hiruna.edutrack
│   │       ├── controller    # REST controllers
│   │       ├── dto           # Data Transfer Objects
│   │       ├── entity        # Database entity models
│   │       ├── repository    # JPA Repositories
│   │       ├── service       # Business logic
│   │       └── util          # Utility classes
│   └── resources
│       ├── application.properties # Configuration
│       └── schema.sql              # Database schema
└── test
    └── java
        └── dev.hiruna.edutrack      # Unit and integration tests
```

## License

This project is licensed under the MIT License. See the LICENSE file for details.


### Contact

- Author: Hiruna Gallage
- Website: [hiruna.dev](https://hiruna.dev)
- Email: [hello@hiruna.dev](mailto:hello@hiruna.dev)