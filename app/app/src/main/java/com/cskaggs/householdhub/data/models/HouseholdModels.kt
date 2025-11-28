package com.cskaggs.householdhub.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Household(
    val id: String,
    val name: String,
    val invite_code: String? = null
)

@Serializable
data class HouseholdMember(
    val id: String,
    val household_id: String,
    val user_id: String,
    val role: String? = null,
    val color: String? = null,
)