MP8 – Authentication Flow Design

Goal: Define how users authenticate in the Household Hub app (MVP), including screens, navigation, and Supabase Auth interactions.

1. Auth Scope for MVP

For the first version, we keep auth simple:

Auth provider: Supabase Email + Password

User types: All users are the same

Password reset: Out of scope for MVP

Social login: Out of scope for MVP

MVP requirements:

User can create an account

User can log in

Session persists across app launches

User can log out

2. Screens Overview
AuthGate (logic only)

Decides whether to show LoginScreen or MainApp based on whether a Supabase session exists.

LoginScreen

Fields: email, password

Actions: log in, go to sign-up

Error + loading states

SignupScreen

Fields: name, email, password, confirm password

Actions: create account, go back to login

Validation, loading, error states

MainApp (MVP placeholder)

Shows “You are logged in as <email>”

Includes “Log out” button

Future (Not MVP): ForgotPasswordScreen
3. High-Level Navigation Flow
App Launch

AuthGate
→ If session exists → MainApp
→ Otherwise → LoginScreen

Login Flow

LoginScreen
→ User enters credentials
→ Call Supabase signInWith(Email)
→ Success: navigate to MainApp
→ Failure: show error

Signup Flow

SignupScreen
→ Validate passwords
→ Call Supabase signUpWith(Email)
→ Success: navigate to MainApp
→ Failure: show error

Logout Flow

MainApp
→ Tap Log out
→ Call supabase.auth.signOut()
→ Navigate back to LoginScreen

4. Supabase Auth Calls (MVP)
Login
supabase.auth.signInWith(Email) {
    email = inputEmail
    password = inputPassword
}

Signup
supabase.auth.signUpWith(Email) {
    email = inputEmail
    password = inputPassword
}

Check session on startup
val session = supabase.auth.currentSessionOrNull()

Current user (for profiles later)
val user = supabase.auth.currentUserOrNull()

Logout
supabase.auth.signOut()

5. UI States
LoginScreen

idle

loading

success

error

SignupScreen

idle

client-side validation error

loading

success

error

6. Navigation Structure (MVP)

Single NavHost with routes:

auth_gate

login

signup

main_app

Flow:

AuthGate → login/signup → main_app → logout → login

7. What MP9 Will Implement

AuthGate composable (session check)

LoginScreen + Supabase login

SignupScreen + Supabase signup

Basic navigation graph between screens

MainApp placeholder with logout button

Clear backstack after login/signup/logout

This completes the authentication foundation.