package com.task.points.ui.fragments.pointsCount.mvi

import com.task.points.appbase.mvi.Action

sealed class PointCountAction : Action {
    data class OnCountChanged(val count: String): PointCountAction()
    object OnGoClick : PointCountAction()
}
