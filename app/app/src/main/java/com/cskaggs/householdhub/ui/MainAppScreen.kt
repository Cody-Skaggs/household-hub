package com.cskaggs.householdhub.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cskaggs.householdhub.data.HouseholdRepository
import com.cskaggs.householdhub.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun MainAppScreen(
    onLogout: () -> Unit
){
    val supabase = SupabaseClientProvider.client
    val scope = rememberCoroutineScope()

    var userEmail by remember { mutableStateOf<String?>(null) }

    // Household State
    var isHouseholdLoading by remember { mutableStateOf(true) }
    var householdName by remember { mutableStateOf<String?>(null) }
    var householdError by remember { mutableStateOf<String?> (null) }

    LaunchedEffect(Unit) {
        //load user email
        val user = supabase.auth.currentUserOrNull()
        userEmail = user?.email

        // load current household from Supabase
        try{
            val household = HouseholdRepository.getCurrentUserHousehold()
            householdName = household?.name
        }catch (e: Exception){
            householdError = "Failed to load household: ${e.message ?: "Unknown Error"}"
        }finally {
            isHouseholdLoading = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Household Hub", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (userEmail != null){
                    "Logged in as $userEmail"
                }else{
                    "Loading user..."
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            //Household section
            Text(
                text = "Household",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            when {
                isHouseholdLoading -> {
                    Text("Checking your household...", style = MaterialTheme.typography.bodySmall)
                }
                householdError != null -> {
                    Text(
                        householdError ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                householdName != null -> {
                    Text(
                        text = "You are in household: $householdName",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                else -> {
                    Text(
                        text = "You are not yet in a household",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                        //Todo:Implement Create Household
                    }
                ) {
                    Text("Create Household")
                }

                OutlinedButton(
                    onClick = {
                        //TODO: Implement Join Household by invite code
                    }
                ) {
                    Text("Join Household")
                }
            }

            Spacer(Modifier.height(32.dp))

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            supabase.auth.signOut()
                        }catch (_: Exception){

                        }
                        onLogout()
                    }
                }
            ) {
                Text("Log out")
            }
        }
    }
}