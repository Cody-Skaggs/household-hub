-- Enable Row Level Security (RLS) on all tables
alter table public.profiles          enable row level security;
alter table public.households        enable row level security;
alter table public.household_members enable row level security;
alter table public.events            enable row level security;

----------------------------------------------------
-- PROFILES POLICIES
----------------------------------------------------

-- Users can view their own profile
create policy "Users can view their own profile"
on public.profiles
for select
using (auth.uid() = id);

-- Users can insert their own profile (id must match auth.uid)
create policy "Users can insert their own profile"
on public.profiles
for insert
with check (auth.uid() = id);

-- Users can update their own profile
create policy "Users can update their own profile"
on public.profiles
for update
using (auth.uid() = id)
with check (auth.uid() = id);

----------------------------------------------------
-- HOUSEHOLDS POLICIES
----------------------------------------------------

-- Only members of a household can view it
create policy "Members can view households they belong to"
on public.households
for select
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = households.id
      and hm.user_id = auth.uid()
  )
);

-- Any authenticated user can create a household
create policy "Authenticated users can create households"
on public.households
for insert
with check (auth.role() = 'authenticated');

-- Only members can update a household
create policy "Members can update households they belong to"
on public.households
for update
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = households.id
      and hm.user_id = auth.uid()
  )
)
with check (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = households.id
      and hm.user_id = auth.uid()
  )
);

-- Only members can delete a household (MVP behavior)
create policy "Members can delete households they belong to"
on public.households
for delete
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = households.id
      and hm.user_id = auth.uid()
  )
);

----------------------------------------------------
-- HOUSEHOLD MEMBERS POLICIES
----------------------------------------------------

-- Users can view their own memberships
create policy "Users can view their own household memberships"
on public.household_members
for select
using (user_id = auth.uid());

-- Users can join households as themselves
create policy "Users can join households as themselves"
on public.household_members
for insert
with check (user_id = auth.uid());

-- Users can leave households they belong to
create policy "Users can leave households they belong to"
on public.household_members
for delete
using (user_id = auth.uid());

----------------------------------------------------
-- EVENTS POLICIES
----------------------------------------------------

-- Members can view events in households they belong to
create policy "Members can view events in their households"
on public.events
for select
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = events.household_id
      and hm.user_id = auth.uid()
  )
);

-- Members can create events in households they belong to
create policy "Members can create events in their households"
on public.events
for insert
with check (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = events.household_id
      and hm.user_id = auth.uid()
  )
);

-- Members can update events in households they belong to
create policy "Members can update events in their households"
on public.events
for update
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = events.household_id
      and hm.user_id = auth.uid()
  )
)
with check (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = events.household_id
      and hm.user_id = auth.uid()
  )
);

-- Members can delete events in households they belong to
create policy "Members can delete events in their households"
on public.events
for delete
using (
  exists (
    select 1
    from public.household_members hm
    where hm.household_id = events.household_id
      and hm.user_id = auth.uid()
  )
);
