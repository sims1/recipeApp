package api

import kotlinx.serialization.Serializable

const val recipeIdParameterKey = "recipeId"

const val authTokenParameterKey = "authToken"
const val loginIdParameterKey = "loginId"
const val loginPasswordParameterKey = "loginPassword"

@Serializable
data class AuthResult(val isAuthenticated: Boolean, val reason: String = "")