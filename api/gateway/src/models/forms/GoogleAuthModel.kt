package me.guillaumewilmot.api.gateway.models.forms

data class GoogleAuthModel(
    val email: String,
    val firstname: String,
    val lastname: String,
    val idToken: String
)