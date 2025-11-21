-- Enable pgcrypto so we can use gen_random_uuid() for UUIDs
create extension if not exists "pgcrypto";

-------------------------------
-- 1. PROFILES
-------------------------------
-- One profile per authenticated user (auth.users).
-- This stores app-specific user info (not passwords).

create table if not exists public.profiles (
  id uuid primary key
    references auth.users (id) on delete cascade,
  name text not null,
  created_at timestamptz not null default now()
);

-------------------------------
-- 2. HOUSEHOLDS
-------------------------------

create table if not exists public.households (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  invite_code text not null unique,
  created_at timestamptz not null default now()
);

-------------------------------
-- 3. HOUSEHOLD MEMBERS
-------------------------------
-- Join table: which users belong to which households.

create table if not exists public.household_members (
  id uuid primary key default gen_random_uuid(),
  household_id uuid not null
    references public.households (id) on delete cascade,
  user_id uuid not null
    references public.profiles (id) on delete cascade,
  role text not null default 'MEMBER',
  color text, -- optional per-household color for this user
  joined_at timestamptz not null default now(),
  constraint household_members_unique_member unique (household_id, user_id)
);

-- Index to quickly find households for a given user
create index if not exists household_members_user_id_idx
  on public.household_members (user_id);

-------------------------------
-- 4. EVENTS
-------------------------------

create table if not exists public.events (
  id uuid primary key default gen_random_uuid(),
  household_id uuid not null
    references public.households (id) on delete cascade,
  created_by uuid not null
    references public.profiles (id) on delete cascade,
  title text not null,
  description text,
  type text,
  start_time timestamptz not null,
  end_time timestamptz,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

-- Index to fetch events for a household ordered by start time
create index if not exists events_household_start_time_idx
  on public.events (household_id, start_time);
