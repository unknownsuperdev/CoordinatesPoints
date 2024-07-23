package com.task.domain.model.errorhandler

sealed class ApiException(
    message: String?
) : Throwable(message)

class MobApiException(
    val code: Int,
    message: String?
) : ApiException(message)


