package com.cskaggs.householdhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cskaggs.householdhub.ui.theme.HouseholdHubTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseholdHubApp()
                }
            }
        }

@Composable
fun HouseholdHubApp(){
    HouseholdHubTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            AppHomeScreen()
        }
    }
}

@Composable
fun AppHomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Household Hub",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "MVP: Shared calendar for your household.",
            style = MaterialTheme.typography.bodyMedium
        )

    }
}