# System Overview

This document provides a high-level technical overview of the Jira Mini system,
including the tech stack, architecture, module structure, and key data flows.

---

## 1. Tech Stack

### Backend

- Java Spring Boot
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Maven

### Frontend

- React (Vite)
- Ant Design
- Axios

### Tools

- Thunder Client — API testing
- GitHub — version control

---

## 2. High-Level Architecture

The system follows a standard 3-tier architecture:

```
┌──────────────────────────────┐
│         Client (React)       │
│  Vite + Ant Design + Axios   │
└─────────────┬────────────────┘
              │ HTTP / REST / JSON
              ▼
┌──────────────────────────────┐
│   Backend API (Spring Boot)  │
│  Spring Security + JWT       │
│  Business Logic              │
│  REST Controllers            │
└─────────────┬────────────────┘
              │ JPA / Hibernate
              ▼
┌──────────────────────────────┐
│        Database (MySQL)      │
└──────────────────────────────┘
```

- Frontend communicates with the backend exclusively via REST APIs
- Backend handles authentication, authorization, business logic, and data persistence
- All API requests (except login and register) require a valid JWT token in the `Authorization: Bearer <token>` header

---

## 3. Backend Module Structure

The backend is organized into the following modules:

| Module         | Responsibilities                                                                       |
| -------------- | -------------------------------------------------------------------------------------- |
| Authentication | Login, Register, JWT handling, Security configuration                                  |
| User           | System user management, role management                                                |
| Workspace      | Workspace creation and membership                                                      |
| Project        | Project management inside a workspace                                                  |
| Board          | Trello-style board — displays tasks with assignee, priority, due date, and done status |
| Task           | Task lifecycle management, comments                                                    |

---

## 4. Key Data Flows

### Flow: User Login

1. User sends a login request with email and password
2. Backend validates the credentials
3. Backend generates and returns a JWT token
4. Client stores the token locally
5. All subsequent requests include the token in the `Authorization: Bearer <token>` header

### Flow: Create Project

1. Workspace Owner sends a create project request
2. Backend validates workspace membership and permissions
3. Backend saves the project to the database
4. Project data is returned to the client

### Flow: Task Lifecycle

1. Project Manager creates a task on the board
2. Backend saves the task with `done = false`
3. Task is assigned to a member
4. Member marks the task as done
5. Backend updates `done = true` and the board reflects the updated status
