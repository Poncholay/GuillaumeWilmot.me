package me.guillaumewilmot.api.models.forms

data class GoogleAuthModel(
    val email: String,
    val firstname: String,
    val lastname: String,
    val idToken: String
)