package com.cskaggs.householdhub.data

import android.content.Context
import com.cskaggs.householdhub.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.ktor.client.engine.android.Android

object SupabaseClientProvider {
    lateinit var client: SupabaseClient
        private set

    fun init(context: Context) {
        if (::client.isInitialized) return

        client = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY
        ) {
            httpEngine = Android.create()

            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }
}
