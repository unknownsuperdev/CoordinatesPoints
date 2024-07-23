package com.task.points.ui.fragments.pointsCount.mvi

import com.task.domain.model.errorhandler.UiError
import com.task.points.appbase.mvi.State

data class PointCountState(
    override val showNetworkError: Boolean? = null,
    override val apiException: UiError? = null,
    val countState: CountState = CountState()
): State
