package com.cskaggs.householdhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cskaggs.householdhub.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun AuthGateScreen(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    val supabase = SupabaseClientProvider.client
    val scope = rememberCoroutineScope()

    var status by remember { mutableStateOf("Checking session...") }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val session = supabase.auth.currentSessionOrNull()
                if (session != null) {
                    status = "Session found, logging you in..."
                    onAuthenticated()
                } else {
                    status = "No session, please log in."
                    onUnauthenticated()
                }
            } catch (e: Exception) {
                // If anything goes wrong, just send user to login
                status = "Error checking session. Please log in."
                onUnauthenticated()
            }
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
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text(text = status)
        }
    }
}
