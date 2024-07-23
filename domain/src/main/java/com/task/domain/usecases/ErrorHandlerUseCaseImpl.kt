package com.task.domain.usecases

import com.google.gson.Gson
import com.task.domain.model.errorhandler.UiError
import com.task.domain.model.errorhandler.UiErrorInfo
import org.koin.core.annotation.Factory
import retrofit2.HttpException
import java.net.UnknownHostException

private const val NETWORK_ERROR_CODE = 1000

interface ErrorHandlerUseCase {
    operator fun invoke(
        throwable: Throwable,
        repeatEvent: () -> Unit = {}
    ): UiError
}

@Factory
internal class ErrorHandlerUseCaseImpl : ErrorHandlerUseCase {

    override operator fun invoke(
        throwable: Throwable,
        repeatEvent: () -> Unit
    ): UiError {

        when (throwable) {
            is HttpException -> {
                val errorJson = throwable.response()
                    ?.errorBody()
                    ?.string()
                    .orEmpty()

                val errorMessage = throwable.message.orEmpty()

                var errorInfo = try {
                    Gson().fromJson(errorJson, UiErrorInfo::class.java)
                } catch (_: Exception) {
                    UiErrorInfo(throwable.code(), errorJson)
                }
                if (errorInfo.message.isNullOrEmpty()) {
                    errorInfo = errorInfo.copy(message = errorMessage)
                }
                return UiError(errorInfo)
            }
            is UnknownHostException -> {
                return UiError(UiErrorInfo(NETWORK_ERROR_CODE))
            }
//            is ApiException -> {
//                 UiError(
//                    if (throwable is RestApiException && throwable.code == RestApiCodes.PASSWORD_ALREADY_USED) {
//                        stringProvider.getString(R.string.password_expired_used_password_error_text)
//                    } else {
//                        throwable.message
//                            ?: stringProvider.getString(R.string.api_error_general_text)
//                    },
//                    R.drawable.img_load_failed,
//                )
//            }
            else -> {
                // _apiException.emit(getString(R.string.error))
            }
        }
        return UiError(UiErrorInfo())
    }
}

