package com.cskaggs.householdhub.data

import com.cskaggs.householdhub.data.models.Household
import com.cskaggs.householdhub.data.models.HouseholdMember
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.result.PostgrestResult

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
        TODO("Implement createHousehold")
    }

    suspend fun joinHousehold(inviteCode: String): Household {
        TODO("Implement joinHousehold")
    }
}