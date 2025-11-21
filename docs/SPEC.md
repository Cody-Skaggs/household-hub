# Household Hub – Product Specification (MVP)

## 1. One-sentence pitch

This app is a solution for multifamily households, roommates, and smaller families that gives the household a centralized hub to organize schedules — like work shifts, events, and even who’s in charge of dinner each day.

## 2. Target users

- Multifamily households
- Roommates (dorms, co-renters)
- “Typical” households (parents + kids, couples, etc.)

## 3. MVP – Core goals

The MVP should:

1. Provide a **shared, real-time calendar** for everyone in a household.
2. Let users **create and join households**.
3. Let users **create and view events** on the shared calendar.

## 4. MVP features (v1 only)

### 4.1 Must-have features

1. **Real-time synced calendar**
   - A shared calendar that shows upcoming events.
   - Everyone in the same household sees the same events.
   - Events update as soon as they are created or modified.

2. **Creating and joining households**
   - A user can create a household (e.g. “Skaggs Family” or “Unit 304”).
   - A user can join an existing household using an invite code.

3. **Creating events on the calendar**
   - Any household member can add events (work shifts, appointments, family events, etc.).
   - Basic event info: title, time, optional description, event type.
   - Events appear for all members of that household.

### 4.2 Explicitly **not** in MVP (future ideas)

These are intentionally postponed until after v1:

- Real-time synced **grocery list**
- Real-time synced **daily chore list**
- A **“dinner ready”** button/notification feature
- Integrations with Google/Apple calendars
- In-app chat
- Complex roles/permissions beyond basic “owner/member”

## 5. Platforms & tech stack

- **Mobile client:** Android app using Kotlin + Jetpack Compose
- **Backend:** Supabase (Postgres, Auth, Realtime)

## 6. Success criteria for MVP

The MVP is “good enough to use” when:

- A user can sign up / log in.
- A user can create or join a household.
- All members of a household see the same events.
- Events can be created and viewed reliably.
- The experience is simple and understandable for non-technical family members.
