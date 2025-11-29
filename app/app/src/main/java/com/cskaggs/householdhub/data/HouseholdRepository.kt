package com.cskaggs.householdhub.data

import android.health.connect.datatypes.units.Length
import com.cskaggs.householdhub.data.models.Household
import com.cskaggs.householdhub.data.models.HouseholdMember
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlin.random.Random

object HouseholdRepository {
    private val supabase get() = SupabaseClientProvider.client

    // returns the first household the current user belongs to or null if none

    suspend fun getCurrentUserHousehold(): Household? {
        val user = supabase.auth.currentUserOrNull() ?: return null
        val userId = user.id

        // 1) find household_members rows for this user
        val memberships = supabase.postgrest["household_members"]
            .select {
                filter {
                    eq("user_id", userId)
                }
            }
            .decodeList<HouseholdMember>()

        val membership = memberships.firstOrNull() ?: return null


        //fetch the household for that membership
        val households = supabase.postgrest["households"]
            .select {
                filter {
                    eq("id", membership.household_id)
                }
            }
            .decodeList<Household>()

        return households.firstOrNull()
    }

    //Implement these later
    suspend fun createHousehold(name: String): Household {
        val user = supabase.auth.currentUserOrNull()
            ?: throw IllegalStateException("No logged-in user")

        val userId = user.id
        val inviteCode = generateInviteCode()

        // 1) Insert household and ask PostgREST to return the inserted row
        val household = supabase.postgrest["households"]
            .insert(
                mapOf(
                    "name" to name,
                    "invite_code" to inviteCode
                )
            ) {
                // This sets Prefer: return=representation so the body contains JSON
                select()
            }
            .decodeSingle<Household>()

        // 2) Insert membership row for the current user
        supabase.postgrest["household_members"].insert(
            mapOf(
                "household_id" to household.id,
                "user_id" to userId,
                "role" to "owner"
            )
        )

        return household
    }


    suspend fun joinHousehold(inviteCode: String): Household {
        val user = supabase.auth.currentUserOrNull()
            ?: throw IllegalStateException("No logged in user")

        val userId = user.id

        val households = supabase.postgrest["households"]
            .select {
                filter { eq("invite_code", inviteCode) }
            }
            .decodeList<Household>()

        val household = households.firstOrNull()
            ?: throw IllegalArgumentException("No household found for that invite code")

        supabase.postgrest["household_members"].insert(
            mapOf(
                "household_id" to household.id,
                "user_id" to userId,
                "role" to "member"
            )
        )

        return household
    }

    private fun generateInviteCode(length: Int = 8): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length)
            .map { chars[Random.nextInt(chars.length)] }
            .joinToString("")
    }
}