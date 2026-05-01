# Implementation Plan

This document outlines the development phases for the Jira Mini project.
The goal is to build a working MVP first, then polish and extend.
Backend is completed first before moving to frontend integration.

---

## Overview

| Phase    | Focus                | Status         |
| -------- | -------------------- | -------------- |
| Phase 1  | Project Setup        | ⬜ Not started |
| Phase 2  | Authentication       | ⬜ Not started |
| Phase 3  | Infrastructure       | ⬜ Not started |
| Phase 4  | Authorization        | ⬜ Not started |
| Phase 5  | User Management      | ⬜ Not started |
| Phase 6  | Workspace & Members  | ⬜ Not started |
| Phase 7  | Project & Members    | ⬜ Not started |
| Phase 8  | Board                | ⬜ Not started |
| Phase 9  | Task Management      | ⬜ Not started |
| Phase 10 | Comments             | ⬜ Not started |
| Phase 11 | Frontend             | ⬜ Not started |
| Phase 12 | Integration & Polish | ⬜ Not started |

---

## Phase 1 — Project Setup

**Goal:** Initialize the project structure and development environment.

### Backend

- [x] Initialize Spring Boot project (Maven)
- [ ] Configure MySQL database connection
- [ ] Set up JPA / Hibernate
- [ ] Configure Spring Security (basic setup)
- [ ] Set up project package structure (`controller`, `service`, `repository`, `model`, `dto`, `config`)
- [ ] Create MySQL database and run initial schema migration

### Frontend

- [x] Initialize React project with Vite
- [ ] Install and configure Ant Design
- [ ] Install Axios
- [ ] Set up folder structure (`pages`, `components`, `services`, `hooks`, `utils`)

---

## Phase 2 — Authentication

**Goal:** Allow users to register, login, and access protected routes via JWT.

### Backend

- [x] Create `users`, `auth_identities`, `auth_credentials` tables
- [ ] Hash password with BCrypt before saving (`auth_credentials`)
- [ ] Check email uniqueness on register — return 409 if already exists
- [ ] Implement `POST /auth/register`
- [ ] Implement `POST /auth/login` — return JWT token
- [ ] Implement `POST /auth/logout`
- [ ] Configure JWT filter in Spring Security
- [ ] Protect all routes except `/auth/**`

---

## Phase 3 — Infrastructure

**Goal:** Set up cross-cutting backend concerns that all subsequent phases depend on.
These must be in place before building any business logic.

### Error Handling

- [ ] Create global exception handler (`@ControllerAdvice`)
- [ ] Define standard error response format (`code`, `message`, `details`)
- [ ] Handle common exceptions: `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `409 Conflict`

### Response Wrapper

- [ ] Wrap all API responses in a standard format: `{ success, data, message }`

### Logging

- [ ] Configure application logging (SLF4J / Logback)
- [ ] Log incoming requests and outgoing responses
- [ ] Log errors with stack traces

### CORS Configuration

- [ ] Configure CORS to allow requests from the frontend origin
- [ ] Allow required HTTP methods and headers

### Pagination / Sorting / Filtering

- [ ] Define reusable pagination request/response structure (`page`, `size`, `sortBy`, `direction`)
- [ ] Apply consistently across all list endpoints

---

## Phase 4 — Authorization

**Goal:** Enforce role-based access control (RBAC) across all modules.

### Role Design

- [ ] Define roles: `ADMIN`, `OWNER`, `PM`, `MEMBER`
- [ ] Create `roles` table with seed data (ADMIN, OWNER, PM, MEMBER)
- [ ] Assign default role `MEMBER` on register

### Access Control

- [ ] Implement role extraction from JWT token
- [ ] Create authorization utility / helper to check roles per request
- [ ] Enforce system role checks (Admin-only endpoints)
- [ ] Enforce workspace role checks (Owner-only actions)
- [ ] Enforce project role checks (PM-only actions)
- [ ] Return `403 Forbidden` for unauthorized access attempts

---

## Phase 5 — User Management

**Goal:** Allow Admin to manage system users and all users to manage their own profile.

### Backend

- [ ] Implement `GET /users` — with search, filter, pagination, sorting (Admin only)
- [ ] Implement `GET /users/{id}` (Admin only)
- [ ] Implement `PATCH /users/{id}/role` — change system role (Admin only)
- [ ] Implement `PATCH /users/{id}/status` — activate / deactivate (Admin only)
- [ ] Implement `GET /users/me`
- [ ] Implement `PATCH /users/me`

---

## Phase 6 — Workspace & Members

**Goal:** Allow Workspace Owners to create workspaces and manage members.

### Backend

- [ ] Create `workspaces` and `workspace_members` tables
- [ ] Implement `POST /workspaces`
- [ ] Implement `GET /workspaces/{id}`
- [ ] Implement `PUT /workspaces/{id}`
- [ ] Implement `DELETE /workspaces/{id}`
- [ ] Implement `GET /workspaces/{id}/members` — with pagination
- [ ] Implement `POST /workspaces/{id}/members` — invite by email
- [ ] Implement `DELETE /workspaces/{id}/members/{userId}`
- [ ] Implement `PATCH /workspaces/{id}/members/{userId}/role`

---

## Phase 7 — Project & Members

**Goal:** Allow Workspace Owners to create projects and manage project members.

### Backend

- [ ] Create `projects` and `project_members` tables
- [ ] Implement `POST /workspaces/{workspaceId}/projects`
- [ ] Implement `GET /workspaces/{workspaceId}/projects` — with pagination
- [ ] Implement `GET /projects/{id}`
- [ ] Implement `PUT /projects/{id}`
- [ ] Implement `DELETE /projects/{id}`
- [ ] Implement `GET /projects/{id}/members`
- [ ] Implement `POST /projects/{id}/members`
- [ ] Implement `DELETE /projects/{id}/members/{userId}`

---

## Phase 8 — Board

**Goal:** Allow Project Managers to create boards inside a project.

### Backend

- [ ] Create `boards` table
- [ ] Implement `POST /projects/{projectId}/boards`
- [ ] Implement `GET /projects/{projectId}/boards`
- [ ] Implement `GET /boards/{id}`
- [ ] Implement `DELETE /boards/{id}`

---

## Phase 9 — Task Management

**Goal:** Implement the full task lifecycle on the board.

### Backend

- [ ] Create `tasks` and `task_priorities` tables with seed data
- [ ] Implement `POST /boards/{boardId}/tasks`
- [ ] Implement `GET /boards/{boardId}/tasks` — with filter (assignee, done), pagination, sorting
- [ ] Implement `GET /tasks/{id}`
- [ ] Implement `PUT /tasks/{id}`
- [ ] Implement `DELETE /tasks/{id}`
- [ ] Implement `PATCH /tasks/{id}/assign`
- [ ] Implement `PATCH /tasks/{id}/done`

---

## Phase 10 — Comments

**Goal:** Allow team members to communicate within a task.

### Backend

- [ ] Create `comments` table
- [ ] Implement `POST /tasks/{taskId}/comments`
- [ ] Implement `GET /tasks/{taskId}/comments` — with pagination
- [ ] Implement `PUT /comments/{id}`
- [ ] Implement `DELETE /comments/{id}`

---

## Phase 11 — Frontend

**Goal:** Build the complete UI and connect all pages to the backend APIs.

### API Layer

- [ ] Configure Axios base URL and interceptors for JWT
- [ ] Create service files per module (`authService`, `workspaceService`, `taskService`, etc.)
- [ ] Handle API errors globally (401 redirect, error toast)
- [ ] Redirect to login if token is missing or expired

### Authentication

- [ ] Register page
- [ ] Login page
- [ ] Store JWT token (localStorage)

### Dashboard

- [ ] Dashboard page with role-based content

### User Management

- [ ] User list page (Admin only)
- [ ] Update own profile page

### Workspace

- [ ] Workspace dashboard page
- [ ] Create workspace form
- [ ] Member list and management UI

### Project

- [ ] Project list page
- [ ] Project overview page
- [ ] Create project form
- [ ] Project member management UI

### Board

- [ ] Board list inside project
- [ ] Create board form
- [ ] Board view page

### Task

- [ ] Task list on board
- [ ] Create task form (title, description, priority, due date)
- [ ] Task detail view
- [ ] Assign task to member
- [ ] Mark task as done / undone
- [ ] Filter tasks by assignee or done status

### Comments

- [ ] Comment thread inside task detail view
- [ ] Add / edit / delete comment UI

### General

- [ ] Role-based UI rendering (hide/show actions based on role)
- [ ] Loading states and error handling
- [ ] Form validation

---

## Phase 12 — Integration & Polish

**Goal:** Finalize the project for portfolio presentation.

- [ ] End-to-end testing of all major flows
- [ ] Fix bugs and edge cases
- [ ] Clean up code and add comments
- [ ] Write README with screenshots and setup instructions
- [ ] Deploy backend (Railway or Render)
- [ ] Deploy frontend (Vercel or Netlify)
- [ ] Add live demo link to README
