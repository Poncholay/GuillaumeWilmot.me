package me.guillaumewilmot.powerlifting.models.responses

data class ApiResponse<T> (
    var message: String? = null,
    var data: T? = null
)
