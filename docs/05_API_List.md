# API List

This document lists all REST API endpoints for the Jira Mini backend.
All endpoints are prefixed with `/api`.
All requests (except Auth) require `Authorization: Bearer <token>` header.

---

## Authentication

| Method | Endpoint         | Description                 | Auth Required |
| ------ | ---------------- | --------------------------- | :-----------: |
| POST   | `/auth/register` | Register a new account      |      NO       |
| POST   | `/auth/login`    | Login and receive JWT token |      NO       |
| POST   | `/auth/logout`   | Logout and invalidate token |      YES      |

---

## Users

| Method | Endpoint             | Description                    | Role  |
| ------ | -------------------- | ------------------------------ | ----- |
| GET    | `/users`             | Get all users (search, filter) | Admin |
| GET    | `/users/{id}`        | Get user by ID                 | Admin |
| PATCH  | `/users/{id}/role`   | Change user system role        | Admin |
| PATCH  | `/users/{id}/status` | Activate / deactivate user     | Admin |
| GET    | `/users/me`          | Get current user profile       | All   |
| PATCH  | `/users/me`          | Update current user profile    | All   |

---

## Workspaces

| Method | Endpoint           | Description            | Role            |
| ------ | ------------------ | ---------------------- | --------------- |
| POST   | `/workspaces`      | Create a new workspace | Workspace Owner |
| GET    | `/workspaces/{id}` | Get workspace details  | Member+         |
| PUT    | `/workspaces/{id}` | Update workspace info  | Workspace Owner |
| DELETE | `/workspaces/{id}` | Delete workspace       | Workspace Owner |

### Workspace Members

| Method | Endpoint                                 | Description                        | Role            |
| ------ | ---------------------------------------- | ---------------------------------- | --------------- |
| GET    | `/workspaces/{id}/members`               | Get all workspace members          | Member+         |
| POST   | `/workspaces/{id}/members`               | Invite a member by email           | Workspace Owner |
| DELETE | `/workspaces/{id}/members/{userId}`      | Remove a member                    | Workspace Owner |
| PATCH  | `/workspaces/{id}/members/{userId}/role` | Change member role (promote to PM) | Workspace Owner |

---

## Projects

| Method | Endpoint                             | Description                   | Role            |
| ------ | ------------------------------------ | ----------------------------- | --------------- |
| POST   | `/workspaces/{workspaceId}/projects` | Create a new project          | Workspace Owner |
| GET    | `/workspaces/{workspaceId}/projects` | Get all projects in workspace | Member+         |
| GET    | `/projects/{id}`                     | Get project details           | Member+         |
| PUT    | `/projects/{id}`                     | Update project info           | Workspace Owner |
| DELETE | `/projects/{id}`                     | Delete project                | Workspace Owner |

### Project Members

| Method | Endpoint                          | Description                | Role            |
| ------ | --------------------------------- | -------------------------- | --------------- |
| GET    | `/projects/{id}/members`          | Get all project members    | Member+         |
| POST   | `/projects/{id}/members`          | Add member to project      | Workspace Owner |
| DELETE | `/projects/{id}/members/{userId}` | Remove member from project | Workspace Owner |

---

## Boards

| Method | Endpoint                       | Description               | Role                |
| ------ | ------------------------------ | ------------------------- | ------------------- |
| POST   | `/projects/{projectId}/boards` | Create a new board        | Workspace Owner, PM |
| GET    | `/projects/{projectId}/boards` | Get all boards in project | Member+             |
| GET    | `/boards/{id}`                 | Get board details         | Member+             |
| DELETE | `/boards/{id}`                 | Delete board              | Workspace Owner, PM |

---

## Tasks

| Method | Endpoint                  | Description                                          | Role                          |
| ------ | ------------------------- | ---------------------------------------------------- | ----------------------------- |
| POST   | `/boards/{boardId}/tasks` | Create a new task                                    | Workspace Owner, PM, Member   |
| GET    | `/boards/{boardId}/tasks` | Get all tasks on board (filter by assignee, done)    | Member+                       |
| GET    | `/tasks/{id}`             | Get task details                                     | Member+                       |
| PUT    | `/tasks/{id}`             | Update task (title, description, priority, due date) | Workspace Owner, PM, own task |
| DELETE | `/tasks/{id}`             | Delete task                                          | Workspace Owner, PM           |
| PATCH  | `/tasks/{id}/assign`      | Assign task to a member                              | Workspace Owner, PM           |
| PATCH  | `/tasks/{id}/done`        | Toggle task done / undone                            | Assignee, Workspace Owner, PM |

---

## Comments

| Method | Endpoint                   | Description                | Role                               |
| ------ | -------------------------- | -------------------------- | ---------------------------------- |
| POST   | `/tasks/{taskId}/comments` | Add a comment to a task    | Member+                            |
| GET    | `/tasks/{taskId}/comments` | Get all comments on a task | Member+                            |
| PUT    | `/comments/{id}`           | Edit a comment             | Comment owner                      |
| DELETE | `/comments/{id}`           | Delete a comment           | Comment owner, PM, Workspace Owner |
