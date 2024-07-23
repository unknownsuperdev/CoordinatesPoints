package com.task.points.ui.fragments.pointsList.mvi

import com.github.mikephil.charting.data.Entry
import com.task.points.appbase.mvi.Reducer
import org.koin.core.annotation.Single

@Single
class PointsListReducer : Reducer<PointsListAction, PointsListState> {

    override fun reduce(
        action: PointsListAction,
        state: PointsListState
    ): PointsListState =
        when (action) {
            is PointsListAction.GetPoints -> state
            is PointsListAction.UpdatePoints -> state.copy(
                points = action.points,
                entries = action.points.map { Entry(it.x.toFloat(), it.y.toFloat()) }
                    .sortedBy { it.x }
            )
            is PointsListAction.OnErrorDialog -> state.copy(
                apiException = action.error
            )
        }
}