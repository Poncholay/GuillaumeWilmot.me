package me.guillaumewilmot.api.models.exceptions

import io.ktor.http.HttpStatusCode
import me.guillaumewilmot.api.MSG_HTTP_403

class HttpForbiddenException : HttpException {
    constructor() : this(MSG_HTTP_403)
    constructor(message: String) : super(HttpStatusCode.Forbidden, message)
}