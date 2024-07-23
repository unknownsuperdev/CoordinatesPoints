package com.task.points.ui.fragments.pointsCount.mvi

import com.task.points.appbase.mvi.Reducer
import com.task.points.ui.fragments.pointsCount.mvi.CountState.Companion.updateValidationAndCopy
import org.koin.core.annotation.Single

@Single
class PointCountReducer : Reducer<PointCountAction, PointCountState> {

    override fun reduce(
        action: PointCountAction,
        state: PointCountState
    ): PointCountState =
        when (action) {
            is PointCountAction.OnCountChanged -> {
                state.copy(
                    countState = action.count.updateValidationAndCopy(state.countState)
                )
            }
            is PointCountAction.OnGoClick -> state
        }
}