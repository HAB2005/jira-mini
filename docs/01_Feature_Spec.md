This document describes the initial feature scope for the Jira Mini project.
The goal is to define the core roles, permissions and system modules for the MVP version.

# Feature Specification

---

# Role System

## Role: ADMIN (System)

### Responsibilities

- Manage system users
- Monitor system activity

### Permissions

- View / search / filter all users
- Change user system roles
- Deactivate user accounts
- View all workspaces, projects and tasks (read-only)

### Limitations

- Cannot participate in project workflow
- Cannot create / edit project data

---

## Role: WORKSPACE OWNER

> Owns and manages the entire workspace.

### Responsibilities

- Own a workspace
- Manage projects and members inside workspace

### Permissions

- Create / update / delete workspace projects
- Invite / remove members to workspace
- Add / remove members from projects
- Promote member to Project Manager
- Full visibility of workspace data

### Limitations

- Cannot manage system users
- Cannot access other workspaces

---

## Role: PROJECT MANAGER

> Direct manager of the team inside a project.

### Responsibilities

- Manage daily work inside project
- Coordinate tasks & workflow

### Permissions

- Create / edit / delete boards
- Create / edit / delete tasks
- Assign / reassign tasks
- Change task status / priority / due date
- View project progress dashboard

### Limitations

- Cannot create / delete project
- Cannot invite or remove members
- Cannot change roles

---

## Role: MEMBER

### Responsibilities

- Work on assigned tasks
- Update task progress
- Collaborate via comments

### Permissions

- View workspace they belong to
- View projects they belong to
- View boards & tasks
- Create tasks
- Update own tasks
- Mark own tasks as done / undone
- Comment on tasks
- Update own profile

### Limitations

- Cannot assign tasks to others
- Cannot manage members
- Cannot manage project settings

---

# System Modules

## Authentication Module

Handles user authentication and account management.

**Main features:**

- Register account
- Login / Logout
- JWT authentication
- Update profile

**Used by:** All roles

---

## User Management Module

System-level user administration.

**Main features:**

- View user list
- Change system roles
- Deactivate accounts

**Used by:** Admin

---

## Workspace Module

Represents an organization or team space that contains multiple projects.

**Main features:**

- Create workspace
- Invite members
- Manage workspace members
- View workspace dashboard

**Used by:** Workspace Owner, Admin (read-only)

---

## Project Module

Projects exist inside a workspace and contain boards and tasks.

**Main features:**

- Create / update / delete projects
- Manage project members
- View project overview and progress

**Used by:** Workspace Owner, Project Manager, Member (view only)

---

## Board Module

Trello-style board for visualizing task progress within a project.
Provides a visual overview of task status within a project.
The board gives an overview of who has completed their tasks and who hasn't.

> **Note:** Kanban-style columns (To Do / In Progress / Done) are not in scope for now and may be considered in a future version.

**Main features:**

- Create / delete boards
- View all tasks on the board
- Filter tasks by member or status

**Used by:** Workspace Owner, Project Manager (create/manage boards), Member (view)

---

## Task Module

Core work management module.

> **Note:** Comments are part of this module for now. If the comment system grows in complexity (replies, mentions, etc.), it should be extracted into a separate Comment Module in the future.

**Main features:**

- View tasks
- Create / edit / delete tasks
- Assign tasks to members
- Set priority and due date
- Mark task as done / undone
- Add comments to tasks

**Used by:** Workspace Owner, Project Manager, Member

# Permission Matrix

> ✅ = Allowed ; ❌ = Not allowed ; 👁 = Read-only

### Authentication

| Action                    | Admin | Workspace Owner | Project Manager | Member |
| ------------------------- | :---: | :-------------: | :-------------: | :----: |
| Register / Login / Logout |  ✅   |       ✅        |       ✅        |   ✅   |
| Update own profile        |  ✅   |       ✅        |       ✅        |   ✅   |

### User Management

| Action                       | Admin | Workspace Owner | Project Manager | Member |
| ---------------------------- | :---: | :-------------: | :-------------: | :----: |
| View / search / filter users |  ✅   |       ❌        |       ❌        |   ❌   |
| Change user system roles     |  ✅   |       ❌        |       ❌        |   ❌   |
| Deactivate user accounts     |  ✅   |       ❌        |       ❌        |   ❌   |

### Workspace

| Action                            | Admin | Workspace Owner | Project Manager | Member |
| --------------------------------- | :---: | :-------------: | :-------------: | :----: |
| Create workspace                  |  ❌   |       ✅        |       ❌        |   ❌   |
| View workspace                    |   👁   |       ✅        |       ✅        |   ✅   |
| Invite / remove members           |  ❌   |       ✅        |       ❌        |   ❌   |
| Promote member to Project Manager |  ❌   |       ✅        |       ❌        |   ❌   |

### Project

| Action                           | Admin | Workspace Owner | Project Manager | Member |
| -------------------------------- | :---: | :-------------: | :-------------: | :----: |
| Create / update / delete project |  ❌   |       ✅        |       ❌        |   ❌   |
| Manage project members           |  ❌   |       ✅        |       ❌        |   ❌   |
| View project overview            |   👁   |       ✅        |       ✅        |   ✅   |

### Board

| Action                | Admin | Workspace Owner | Project Manager | Member |
| --------------------- | :---: | :-------------: | :-------------: | :----: |
| Create / delete board |  ❌   |       ✅        |       ✅        |   ❌   |
| View board            |   👁   |       ✅        |       ✅        |   ✅   |
| Filter tasks on board |  ❌   |       ✅        |       ✅        |   ✅   |

### Task

| Action                         | Admin | Workspace Owner | Project Manager | Member |
| ------------------------------ | :---: | :-------------: | :-------------: | :----: |
| View tasks                     |   👁   |       ✅        |       ✅        |   ✅   |
| Create tasks                   |  ❌   |       ✅        |       ✅        |   ✅   |
| Edit / delete any task         |  ❌   |       ✅        |       ✅        |   ❌   |
| Edit / delete own task         |  ❌   |       ✅        |       ✅        |   ✅   |
| Assign tasks to members        |  ❌   |       ✅        |       ✅        |   ❌   |
| Set priority / due date        |  ❌   |       ✅        |       ✅        |   ❌   |
| Mark own task as done / undone |  ❌   |       ✅        |       ✅        |   ✅   |
| Comment on tasks               |  ❌   |       ✅        |       ✅        |   ✅   |
