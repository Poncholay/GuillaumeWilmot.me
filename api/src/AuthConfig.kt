package me.guillaumewilmot.api

object AuthConfig {
    data class Client(val clientId: String = "", val clientSecret: String = "")

    val providers = mapOf(
        "google" to Client(
            clientId = "294753139295-m7bsqrdj8q8gomitc54knag0rnguopf3.apps.googleusercontent.com",
            clientSecret = "OXshERyBqV3Jq7iqBlnZRc3s"
        ),
        "googleAndroid" to Client(
            clientId = "294753139295-hta54llhb1tkmhvbf110sg4l5k79ugru.apps.googleusercontent.com"
        )
    )
}