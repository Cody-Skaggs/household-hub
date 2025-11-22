# MP5 – RLS Testing & Verification

Goal: Verify that Row Level Security (RLS) policies for `profiles`, `households`, `household_members`, and `events` behave as intended.

## Test Setup

We will use two test users:

- **Alice** – should own a household and see its events.
- **Bob** – should see nothing at first, then see data only after joining the household.

### 1. Create test users (Auth)

In Supabase Dashboard:

1. Go to **Authentication → Users**.
2. Create two users:
   - `alice@example.com`
   - `bob@example.com`
3. Note their user IDs (UUIDs) for use in SQL:
   - `ALICE_ID = ...`
   - `BOB_ID = ...`

### 2. Create profiles

```sql
insert into public.profiles (id, name)
values
  ('ALICE_ID', 'Alice'),
  ('BOB_ID', 'Bob');

---

# ✅ RLS Test Results (Execution Log)

Use this section to record what actually happened when you ran each test in Supabase.

## Test 1 – Profiles Visibility

**Alice Actual Result:**  
-  Alice was only able to view her profile

**Bob Actual Result:**  
-  Bob was only able to view his profile

**Notes:**  
-  With the RLS policies in place each user can only view their own profile and not anyone elses


---

## Test 2 – Households Visibility

**Alice Actual Result:**  
-  Alice was able to view the household that was belonged to

**Bob Actual Result:**  
-  Bob was not able to view any households since he did not belong to one yet

**Notes:**  
-  


---

## Test 3 – Events Visibility

**Alice Actual Result:**  
-  Alice was able to add an event to the household she is member of and was able to view the event as well

**Bob Actual Result:**  
-  bob was not able to view the event because he was not a member of the household

**Notes:**  
-  


---

## Test 4 – Unauthorized Event Insert (Bob Before Joining)

**Bob Insert Attempt Result:**  
-  Bob was not able to add an event into the household as he was not a member

**Notes:**  
-  


---

## Test 5 – Add Bob as Household Member

**Bob Households Result:**  
-  Bob was able to join the household

**Bob Events Result:**  
-  Bob was then able to view events

**Notes:**  
-  


---

## Test 6 – Authorized Event Insert (Bob After Joining)

**Bob Insert Result:**  
-  Bob was able to insert events into the household

**Bob Final Events List:**  
-  Bob was able to see his event and the event alice added

**Alice Final Events List (verification):**  
-  Alice was able to see her event and the event bob had added

**Notes:**  
-  


---

## Test 7 – Profile Insert Protection

**Alice Attempt to Insert Bob's Profile:**  
-  Alice was not able to insert bob's profile because the RLS protects users from inserting other users profiles

**Notes:**  
-  

---

# ✔️ Final Summary

Write a summary of your observations here:

- What worked?  
- What didn’t?  
- Any unexpected errors?  
- Any policies that need adjustment?  

**Summary:**  
-  
