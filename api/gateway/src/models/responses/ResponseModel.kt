package me.guillaumewilmot.api.gateway.models.responses

data class ResponseModel(var message: String = "", var data: Any? = null)
typealias ErrorResponseModel = ResponseModel