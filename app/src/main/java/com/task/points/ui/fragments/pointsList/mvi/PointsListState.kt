package com.task.points.ui.fragments.pointsList.mvi

import com.github.mikephil.charting.data.Entry
import com.task.domain.model.UiPoint
import com.task.domain.model.errorhandler.UiError
import com.task.points.appbase.mvi.State

data class PointsListState(
    override val showNetworkError: Boolean? = null,
    override val apiException: UiError? = null,
    val points: List<UiPoint> = emptyList(),
    val entries: List<Entry> = emptyList()
): State
