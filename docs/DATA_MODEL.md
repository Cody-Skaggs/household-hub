# Household Hub – Data Model & User Flows

This document describes the core entities (tables) and main user flows for the MVP.

---

## 1. Entities (Conceptual)

### 1.1 User & Profile

Authentication (email/password) is handled by Supabase Auth (`auth.users`).  
The app will use a separate `profiles` table to store app-specific user data.

**auth.users (Supabase built-in)**

- `id` (uuid) – Supabase user ID
- `email` (text)
- password and other auth fields are managed by Supabase (not stored by us)

**profiles**

- `id` (uuid, primary key) — same as `auth.users.id`
- `name` (text) — display name for the user
- `created_at` (timestamp) — when the profile was created

> We do **not** store the password in our own tables. Supabase manages authentication.

---

### 1.2 Household

Represents a single shared home/group that users can join.

**households**

- `id` (uuid, primary key)
- `name` (text) — e.g. "Skaggs Family", "Room 304"
- `invite_code` (text, unique) — short code to join the household
- `created_at` (timestamp)

---

### 1.3 Household Member

Represents the relationship between a user and a household.  
This is a join table that allows a user to belong to one or more households (in the future).

**household_members**

- `id` (uuid, primary key)
- `household_id` (uuid, foreign key → households.id)
- `user_id` (uuid, foreign key → profiles.id)
- `role` (text) — e.g. `"OWNER"` or `"MEMBER"` (simple for MVP)
- `color` (text, optional) — per-household color for events by this user
- `joined_at` (timestamp)

Notes:

- We **do not** duplicate the user’s name here, since it lives in `profiles`.
- This table answers “which users belong to which household, and in what role?”.

---

### 1.4 Event

Represents a single calendar item for a household.

**events**

- `id` (uuid, primary key)
- `household_id` (uuid, foreign key → households.id)
- `created_by` (uuid, foreign key → profiles.id)
- `title` (text) — event name
- `description` (text, optional)
- `type` (text, optional) — e.g. `"WORK"`, `"APPOINTMENT"`, `"DINNER"`, etc.
- `start_time` (timestamp) — when the event begins
- `end_time` (timestamp, optional) — when it ends (if applicable)
- `created_at` (timestamp)
- `updated_at` (timestamp)

---

## 2. Relationships

- One `auth.users` row ↔ one `profiles` row (1–1).
- One `household` ↔ many `household_members`.
- One `user` (profile) ↔ many `household_members`.
- One `household` ↔ many `events`.
- One `user` (profile) ↔ many `events` they created.

---

## 3. Main User Flow (MVP)

### 3.1 High-level flow

1. **Open app**
   - If **not logged in** → show Login / Sign Up
   - If **logged in** → go to household selection/overview

2. **Sign up**
   - User enters email, password, and name.
   - App creates a Supabase Auth user (`auth.users`).
   - App creates a `profiles` row with `id = auth.users.id` and `name`.

3. **Log in**
   - User enters email/password.
   - Supabase restores a session for the user.

4. **Household step**
   - App checks if the user has any `household_members` rows.
   - If none:
     - Show options to **Create Household** or **Join with invite code**.
   - If one or more:
     - Show a list of households to choose from (even if MVP only realistically uses one).

5. **Inside a household**
   - App fetches `events` where `household_id` = selected household.
   - Show events in a calendar-style view (or simple list initially).
   - User actions:
     - View existing events
     - Add a new event
     - (Later: edit/delete events, filter by type, etc.)

---

## 4. Future Entities (Not MVP, for reference only)

These are ideas for later versions:

- **grocery_lists**
- **grocery_items**
- **chores**
- **dinner_notifications**

They will likely reference `households` in a similar way to events.
