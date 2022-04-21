package controllers

import io.netty.handler.codec.http.HttpResponseStatus

class HttpError(
    val code: HttpResponseStatus,
    override val message: String
) : Exception(message)
