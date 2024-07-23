package com.task.points.ui.fragments.pointsCount

import com.task.points.appbase.navigation.NavEvent
import com.task.points.appbase.navigation.toDestination
import com.task.points.appbase.viewmodel.MviBaseViewModel
import com.task.points.ui.fragments.pointsCount.mvi.PointCountAction
import com.task.points.ui.fragments.pointsCount.mvi.PointCountIntent
import com.task.points.ui.fragments.pointsCount.mvi.PointCountNavigationCommand
import com.task.points.ui.fragments.pointsCount.mvi.PointCountReducer
import com.task.points.ui.fragments.pointsCount.mvi.PointCountState
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PointCountViewModel(
    private val reducer: PointCountReducer
) :
    MviBaseViewModel<PointCountState, PointCountAction, PointCountIntent, PointCountNavigationCommand>() {

    override fun initState(): PointCountState = PointCountState()

    override fun intentToAction(intent: PointCountIntent): PointCountAction {
        return when (intent) {
            is PointCountIntent.OnCountChanged -> PointCountAction.OnCountChanged(intent.count)
            is PointCountIntent.OpenPointsList -> PointCountAction.OnGoClick
        }
    }

    override fun handleAction(action: PointCountAction) {
        when (action) {
            is PointCountAction.OnCountChanged -> {
                uiState.update { (reducer.reduce(action, it)) }
            }

            is PointCountAction.OnGoClick -> {
                onNavigate(
                    PointCountNavigationCommand.GoToPointsList(uiState.value.countState.count)
                )
            }
        }
    }

    override fun handleNavigation(navigationCommand: PointCountNavigationCommand) =
        when (navigationCommand) {
            is PointCountNavigationCommand.Close -> handleNavigate(NavEvent.Back)
            is PointCountNavigationCommand.GoToPointsList -> handleNavigate(
                PointCountFragmentDirections.actionPointCountToPointsList(navigationCommand.count)
                    .toDestination()
            )
        }
}