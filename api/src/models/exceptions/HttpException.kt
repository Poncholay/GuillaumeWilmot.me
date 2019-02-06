package me.guillaumewilmot.api.models.exceptions

import io.ktor.http.HttpStatusCode

open class HttpException(val code: HttpStatusCode, override val message: String) : RuntimeException(message)