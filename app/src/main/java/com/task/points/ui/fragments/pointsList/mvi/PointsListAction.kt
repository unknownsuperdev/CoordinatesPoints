package com.task.points.ui.fragments.pointsList.mvi

import com.task.domain.model.UiPoint
import com.task.domain.model.errorhandler.UiError
import com.task.points.appbase.mvi.Action

sealed class PointsListAction : Action {
    data class GetPoints(val count: Int): PointsListAction()
    data class UpdatePoints(val points: List<UiPoint>): PointsListAction()
    data class OnErrorDialog(val error: UiError): PointsListAction()
}
