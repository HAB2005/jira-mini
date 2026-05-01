# Database Design

This document describes the database schema for the Jira Mini system,
including entity relationships, design decisions, and table definitions.

---

## 1. Overview

The system uses MySQL as the main database.

The database is designed to support:

- User authentication and role management
- Workspace and project organization
- Task management and collaboration

---

## 2. Entity Relationship Summary

### Entities & Relationships

| Entity          | Relationship    | Entity                          |
| --------------- | --------------- | ------------------------------- |
| User            | has many        | AuthIdentity                    |
| AuthIdentity    | has one         | AuthCredential (LOCAL only)     |
| User            | belongs to many | Workspace (via WorkspaceMember) |
| Workspace       | has many        | Project                         |
| Workspace       | has many        | WorkspaceMember                 |
| Project         | has many        | Board                           |
| Project         | has many        | ProjectMember                   |
| Project         | has many        | Task                            |
| Board           | has many        | Task                            |
| Task            | belongs to      | TaskPriority                    |
| Task            | assigned to     | User (assignee)                 |
| Task            | created by      | User                            |
| Task            | has many        | Comment                         |
| Comment         | created by      | User                            |
| WorkspaceMember | has one         | Role                            |
| ProjectMember   | has one         | Role                            |

---

### Key Design Decisions

**Authentication is split into 3 tables:**

- `users` - stores identity-agnostic user data (name, avatar, status)
- `auth_identities` - stores login provider info (LOCAL, GOOGLE, GITHUB)
- `auth_credentials` - stores hashed password for LOCAL provider only

This allows the system to support multiple login providers per user without changing the `users` table.

**Roles are stored in a central lookup table:**

- `roles` table stores all role definitions: ADMIN / OWNER / PM / MEMBER
- Both `workspace_members` and `project_members` reference the same `roles` table
- A user can have different roles in different workspaces or projects

**Soft delete is applied on:**

- `workspaces`, `projects`, `boards`, `tasks`, `comments` - all have a `deleted_at` column
- Records are not physically deleted, only marked with a timestamp

**Task status uses a boolean field (`done`):**

- `done = false` - task is not yet completed
- `done = true` - task has been marked as done by the assignee
- Simple Trello-style completion tracking, no intermediate states

**Task priority uses a lookup table (`task_priorities`):**

- Values: LOW / MEDIUM / HIGH
- Stored as a separate table to allow future extensibility

---

## 3. Table Definitions

---

### `users`

Stores core user information, independent of login method.

| Column     | Type         | Description                |
| ---------- | ------------ | -------------------------- |
| id         | BIGINT PK    | Auto-increment primary key |
| email      | VARCHAR(255) | Unique email address       |
| full_name  | VARCHAR(255) | Display name               |
| avatar_url | VARCHAR(500) | Profile picture URL        |
| status     | ENUM         | ACTIVE / INACTIVE          |
| created_at | TIMESTAMP    | Record creation time       |
| updated_at | TIMESTAMP    | Last update time           |

---

### `auth_identities`

Stores login provider information per user. Supports LOCAL, GOOGLE, and GITHUB.

| Column           | Type         | Description                |
| ---------------- | ------------ | -------------------------- |
| id               | BIGINT PK    | Auto-increment primary key |
| user_id          | BIGINT FK    | References `users.id`      |
| provider         | ENUM         | LOCAL / GOOGLE / GITHUB    |
| provider_user_id | VARCHAR(255) | Provider-specific user ID  |
| created_at       | TIMESTAMP    | Record creation time       |

---

### `auth_credentials`

Stores hashed password for LOCAL provider only.

| Column        | Type         | Description                     |
| ------------- | ------------ | ------------------------------- |
| id            | BIGINT PK    | Auto-increment primary key      |
| identity_id   | BIGINT FK    | References `auth_identities.id` |
| password_hash | VARCHAR(255) | Bcrypt hashed password          |
| created_at    | TIMESTAMP    | Record creation time            |

---

### `roles`

Lookup table storing all role definitions used across the system.

| Column      | Type         | Description                 |
| ----------- | ------------ | --------------------------- |
| id          | BIGINT PK    | Auto-increment primary key  |
| name        | VARCHAR(50)  | ADMIN / OWNER / PM / MEMBER |
| description | VARCHAR(255) | Optional description        |

---

### `workspaces`

Represents a team or organization space that contains multiple projects.

| Column      | Type         | Description                |
| ----------- | ------------ | -------------------------- |
| id          | BIGINT PK    | Auto-increment primary key |
| name        | VARCHAR(255) | Workspace name             |
| description | TEXT         | Optional description       |
| owner_id    | BIGINT FK    | References `users.id`      |
| created_at  | DATETIME     | Record creation time       |
| updated_at  | DATETIME     | Last update time           |
| deleted_at  | DATETIME     | Soft delete timestamp      |

---

### `workspace_members`

Junction table linking users to workspaces with a role.

| Column       | Type      | Description                |
| ------------ | --------- | -------------------------- |
| id           | BIGINT PK | Auto-increment primary key |
| workspace_id | BIGINT FK | References `workspaces.id` |
| user_id      | BIGINT FK | References `users.id`      |
| role_id      | BIGINT FK | References `roles.id`      |
| joined_at    | DATETIME  | Time the user joined       |

---

### `projects`

Projects exist inside a workspace and contain boards and tasks.

| Column       | Type         | Description                |
| ------------ | ------------ | -------------------------- |
| id           | BIGINT PK    | Auto-increment primary key |
| workspace_id | BIGINT FK    | References `workspaces.id` |
| name         | VARCHAR(255) | Project name               |
| description  | TEXT         | Optional description       |
| start_date   | DATE         | Project start date         |
| end_date     | DATE         | Project end date           |
| created_by   | BIGINT FK    | References `users.id`      |
| created_at   | DATETIME     | Record creation time       |
| updated_at   | DATETIME     | Last update time           |
| deleted_at   | DATETIME     | Soft delete timestamp      |

---

### `project_members`

Junction table linking users to projects with a role.

| Column     | Type      | Description                |
| ---------- | --------- | -------------------------- |
| id         | BIGINT PK | Auto-increment primary key |
| project_id | BIGINT FK | References `projects.id`   |
| user_id    | BIGINT FK | References `users.id`      |
| role_id    | BIGINT FK | References `roles.id`      |

---

### `boards`

Trello-style boards that belong to a project and display tasks.

| Column     | Type         | Description                |
| ---------- | ------------ | -------------------------- |
| id         | BIGINT PK    | Auto-increment primary key |
| project_id | BIGINT FK    | References `projects.id`   |
| name       | VARCHAR(255) | Board name                 |
| created_by | BIGINT FK    | References `users.id`      |
| created_at | DATETIME     | Record creation time       |
| updated_at | DATETIME     | Last update time           |
| deleted_at | DATETIME     | Soft delete timestamp      |

---

### `task_statuses`

> **Removed from scope.** Task completion is tracked via the `done` boolean field in the `tasks` table instead.

---

### `task_priorities`

Lookup table for task priority levels.

| Column | Type        | Description                |
| ------ | ----------- | -------------------------- |
| id     | BIGINT PK   | Auto-increment primary key |
| name   | VARCHAR(50) | LOW / MEDIUM / HIGH        |

---

### `tasks`

Core table for task management. Each task belongs to a project and optionally a board.

| Column      | Type         | Description                                |
| ----------- | ------------ | ------------------------------------------ |
| id          | BIGINT PK    | Auto-increment primary key                 |
| project_id  | BIGINT FK    | References `projects.id`                   |
| board_id    | BIGINT FK    | References `boards.id` (nullable)          |
| title       | VARCHAR(255) | Task title                                 |
| description | TEXT         | Optional task description                  |
| done        | BOOLEAN      | false = not done, true = done              |
| priority_id | BIGINT FK    | References `task_priorities.id` (nullable) |
| assignee_id | BIGINT FK    | References `users.id` (nullable)           |
| created_by  | BIGINT FK    | References `users.id`                      |
| due_date    | DATE         | Task deadline                              |
| created_at  | DATETIME     | Record creation time                       |
| updated_at  | DATETIME     | Last update time                           |
| deleted_at  | DATETIME     | Soft delete timestamp                      |

---

### `comments`

Comments attached to tasks for team collaboration.

| Column     | Type      | Description                |
| ---------- | --------- | -------------------------- |
| id         | BIGINT PK | Auto-increment primary key |
| task_id    | BIGINT FK | References `tasks.id`      |
| user_id    | BIGINT FK | References `users.id`      |
| content    | TEXT      | Comment content            |
| created_at | DATETIME  | Record creation time       |
| updated_at | DATETIME  | Last update time           |
| deleted_at | DATETIME  | Soft delete timestamp      |
