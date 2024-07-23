package com.task.points.ui.fragments.pointsCount.mvi

import com.task.points.appbase.mvi.Intent

sealed class PointCountIntent : Intent {
    data class OnCountChanged(val count: String): PointCountIntent()
    object OpenPointsList : PointCountIntent()
}
