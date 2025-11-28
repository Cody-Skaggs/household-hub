package com.cskaggs.householdhub.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cskaggs.householdhub.ui.AuthGateScreen
import com.cskaggs.householdhub.ui.LoginScreen
import com.cskaggs.householdhub.ui.SignupScreen
import com.cskaggs.householdhub.ui.MainAppScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost (
        navController = navController,
        startDestination = NavRoute.AuthGate.route,
        modifier = modifier
    ){
        // Auth gate - decide where to go based on session
        composable(NavRoute.AuthGate.route) {
            AuthGateScreen(
                onAuthenticated = {
                    navController.navigate(NavRoute.MainApp.route) {
                        popUpTo(NavRoute.AuthGate.route) { inclusive = true }
                    }
                },
                onUnauthenticated = {
                    navController.navigate(NavRoute.Login.route){
                        popUpTo(NavRoute.AuthGate.route) { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable(NavRoute.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoute.MainApp.route) {
                        popUpTo(NavRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(NavRoute.Signup.route)
                }
            )
        }

        //Signup screen
        composable(NavRoute.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(NavRoute.MainApp.route) {
                        popUpTo(NavRoute.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Main App Shell
        composable(NavRoute.MainApp.route) {
            MainAppScreen(
                onLogout = {
                    navController.navigate(NavRoute.Login.route){
                        popUpTo(NavRoute.MainApp.route) { inclusive = true }
                    }
                }
            )
        }
    }

}