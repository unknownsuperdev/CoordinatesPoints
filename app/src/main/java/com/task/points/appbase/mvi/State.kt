package com.task.points.appbase.mvi

import com.task.domain.model.errorhandler.UiError

interface State {
    val showNetworkError: Boolean?
    val apiException: UiError?
}
