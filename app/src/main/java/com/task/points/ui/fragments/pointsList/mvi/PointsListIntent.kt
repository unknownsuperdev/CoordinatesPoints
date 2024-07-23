package com.task.points.ui.fragments.pointsList.mvi

import com.task.domain.model.UiPoint
import com.task.domain.model.errorhandler.UiError
import com.task.points.appbase.mvi.Intent

sealed class PointsListIntent : Intent {
    data class GetPoints(val count: Int): PointsListIntent()
    data class UpdatePoints(val points: List<UiPoint>): PointsListIntent()
    data class OnErrorDialog(val error: UiError): PointsListIntent()
}
