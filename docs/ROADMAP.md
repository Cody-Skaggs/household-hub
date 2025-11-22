# Project Roadmap – Household Hub

This document tracks the project’s development plan using a series of small, focused **Mini-Projects**.  
Each Mini-Project is designed to be completed in one sitting and builds toward the full MVP.

---

## Phase 1 — Product & Architecture

### **MP1 — Product Spec (Complete)**
Define the app in clear, simple terms:
- One-sentence pitch  
- Target users  
- MVP features  
- Post-MVP ideas  
- Tech stack  

➡️ Output: `docs/SPEC.md`

---

### **MP2 — Data Model & User Flows (Complete)**
Design the conceptual structure of the app:
- Entities (User, Household, HouseholdMember, Event)
- Field lists  
- Relationships  
- Main user flow  

➡️ Output: `docs/DATA_MODEL.md`

---

## Phase 2 — Supabase Foundation

### **MP3 — Supabase Schema (Complete)**
Translate the data model into SQL:
- Create tables  
- Set up extensions  
- Create indexes  

➡️ Output: `docs/SUPABASE_SCHEMA.sql`

---

### **MP4 — RLS (Row Level Security) Policies**
Secure all tables using RLS:
- Define rules (in English)
- Create SQL policies  
- Apply policies in Supabase  

➡️ Output: `docs/RLS_POLICIES.sql`

---

### **MP5 — RLS Testing & Verification**
Ensure RLS behaves correctly:
- Create test users
- Test SELECT, INSERT, UPDATE, DELETE  
- Use “Run as user” in Supabase  
- Validate no data leakage  

➡️ Output: `docs/RLS_TEST_PLAN.md`

---

## Phase 3 — Android Foundation

### **MP6 — Android Project Setup**
Create a clean Android project:
- Jetpack Compose  
- Packages structure  
- Clean Gradle setup  
- Document purpose of each module  

➡️ Output: `app/` folder + notes

---

### **MP7 — Supabase Client Integration**
Connect mobile app to Supabase:
- Store URL & anon key securely  
- Build `SupabaseClientProvider`  
- Test simple API request  

➡️ Output: Kotlin client code

---

## Phase 4 — Authentication

### **MP8 — Auth Flow Design**
Write out the app’s authentication flow:
- Signup  
- Login  
- Session restoring  
- Logout  

➡️ Output: `docs/AUTH_FLOW.md`

---

### **MP9 — Implement Auth**
Add the real login/signup screens:
- Barebones UI (MVP)
- Create profile row
- Error handling  
- Navigation  

➡️ Output: Code in `app/`

---

## Phase 5 — Household & Events

### **MP10 — Household Creation / Join Flow**
Implement:
- Create Household screen  
- Join with invite code  
- Membership creation  

➡️ Output: Screens + DB operations

---

### **MP11 — Event CRUD**
Implement:
- Add Event  
- List Events  
- Update/Delete for MVP  

➡️ Output: Working event feature

---

## Phase 6 — Calendar UI & Real-Time

### **MP12 — Calendar UI + Realtime Updates**
Optional but excellent:
- Month/week/day views  
- Real-time listener for events  
- Visual polish  

➡️ Output: Polished calendar UI

---

# Status Summary

| Mini-Project | Status |
|--------------|--------|
| MP1 – Product Spec | ✔️ Complete |
| MP2 – Data Model | ✔️ Complete |
| MP3 – Schema | ✔️ Complete |
| MP4 – RLS Policies | 🟡 In Progress |
| MP5 – RLS Testing | ⬜ Not Started |
| MP6–MP12 | ⬜ Not Started |

---

# Notes

This roadmap evolves as the project grows.  
Each MP should be documented, committed, and referenced in the repo.

