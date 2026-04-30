# User Flow

This document describes the main user flows in the Jira Mini application.
Each flow represents a complete use-case from the user's perspective.

---

## Flow 1: Authentication

**Actors:** All roles
**Goal:** Allow users to create an account, access the system, and securely sign out.

### Register
1. User navigates to the Register page
2. User fills in name, email, and password
3. System validates the input
4. System creates the account and redirects to the Login page

### Login
1. User navigates to the Login page
2. User enters email and password
3. System validates credentials and issues a JWT token
4. User is redirected to the Dashboard

### Logout
1. User triggers logout
2. Client removes the stored JWT token
3. User is redirected to the Login page

---

## Flow 2: Workspace & Member Management

**Actor:** Workspace Owner
**Goal:** Set up a team environment by creating a workspace and managing its members.

### Create Workspace
1. Workspace Owner logs in and lands on the Dashboard
2. Initiates workspace creation
3. Fills in workspace name and description
4. System creates the workspace and redirects to the Workspace Dashboard

### Invite Member
1. Workspace Owner opens workspace settings
2. Enters the email of the user to invite
3. System checks if the email belongs to an existing account
4. If yes, the user is added to the workspace
5. If not, the invitation remains pending until registration

### Remove Member
1. Workspace Owner opens the member list
2. Selects a member and confirms removal
3. System removes the member from the workspace and all associated projects

### Promote to Project Manager
1. Workspace Owner opens the member list
2. Selects a member and promotes them to Project Manager
3. System updates the member's role within the workspace

---

## Flow 3: Project Setup

**Actors:** Workspace Owner, Project Manager
**Goal:** Create a project, assemble the team, and prepare the board for work.

### Create Project
1. Workspace Owner opens the workspace
2. Initiates project creation
3. Fills in project name and description
4. System creates the project and redirects to the Project Overview

### Add Members to Project
1. Workspace Owner opens the project settings
2. Selects members from the workspace to add
3. System adds the selected members to the project

### Create Board
1. Project Manager opens the project
2. Initiates board creation and fills in the board name
3. System creates the board and redirects to the Board view

---

## Flow 4: Task Lifecycle 

**Actors:** Workspace Owner, Project Manager, Member
**Goal:** Cover the full lifecycle of a task — from creation to completion — including assignment, updates, and progress tracking.

### View Board & Tasks
1. User opens a project and selects a board
2. System displays all tasks with title, assignee, priority, due date, and done status
3. User can filter tasks by assignee or done status

### Create Task
1. Project Manager or Workspace Owner initiates task creation on the board
2. Fills in title, description, priority, and due date
3. System creates the task and displays it on the board

### Assign Task
1. Project Manager or Workspace Owner opens a task
2. Selects a member as the assignee
3. System saves the assignment and the task appears in the member's view

### Update Task
1. User opens a task
2. Edits title, description, priority, or due date
3. System saves the changes

### Mark Task as Done / Undone
1. Assigned member opens their task
2. Marks the task as done or undone
3. System updates the task status and reflects it on the board

---

## Flow 5: Collaboration

**Actors:** Workspace Owner, Project Manager, Member
**Goal:** Enable team communication directly within a task context.

### Comment on Task
1. User opens a task
2. Types a comment and submits
3. System saves the comment and appends it to the comment thread on the task

---

## Flow 6: Dashboard

**Actors:** All roles
**Goal:** Give each user a quick overview of their current work and workspace activity upon login.

### View Dashboard
1. User logs in and lands on the Dashboard
2. System displays relevant information based on the user's role:
   - **Admin:** overview of all users and system activity
   - **Workspace Owner:** list of workspaces and recent project activity
   - **Project Manager:** assigned projects and task progress
   - **Member:** tasks assigned to them and their current done status
3. User selects a workspace or project to navigate deeper into the system
