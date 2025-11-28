package com.cskaggs.householdhub.core.navigation

sealed class NavRoute(val route: String){
    data object AuthGate : NavRoute("auth_gate")
    data object Login : NavRoute("login")
    data object Signup : NavRoute("signup")
    data object MainApp : NavRoute("main_app")
}