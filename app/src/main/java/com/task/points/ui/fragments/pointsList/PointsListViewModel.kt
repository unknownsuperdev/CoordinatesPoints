package com.task.points.ui.fragments.pointsList

import androidx.lifecycle.viewModelScope
import com.task.domain.usecases.ErrorHandlerUseCase
import com.task.domain.usecases.GetPointsUseCase
import com.task.domain.utils.orDefault
import com.task.points.appbase.navigation.NavEvent
import com.task.points.appbase.viewmodel.MviBaseViewModel
import com.task.points.ui.fragments.pointsList.mvi.PointsListAction
import com.task.points.ui.fragments.pointsList.mvi.PointsListIntent
import com.task.points.ui.fragments.pointsList.mvi.PointsListNavigationCommand
import com.task.points.ui.fragments.pointsList.mvi.PointsListReducer
import com.task.points.ui.fragments.pointsList.mvi.PointsListState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PointsListViewModel(
    count: Int,
    private val reducer: PointsListReducer,
    private val getPointsUseCase: GetPointsUseCase,
    private val errorHandler: ErrorHandlerUseCase
) :
    MviBaseViewModel<PointsListState, PointsListAction, PointsListIntent, PointsListNavigationCommand>() {

    init {
        onIntent(PointsListIntent.GetPoints(count))
    }

    override fun initState(): PointsListState = PointsListState()

    override fun intentToAction(intent: PointsListIntent): PointsListAction {
        return when (intent) {
            is PointsListIntent.GetPoints -> PointsListAction.GetPoints(intent.count)
            is PointsListIntent.UpdatePoints -> PointsListAction.UpdatePoints(intent.points)
            is PointsListIntent.OnErrorDialog -> PointsListAction.OnErrorDialog(intent.error)
        }
    }

    override fun handleAction(action: PointsListAction) {
        when (action) {
            is PointsListAction.GetPoints -> getPoints(action.count)
            is PointsListAction.UpdatePoints ->
                uiState.update { (reducer.reduce(action, it)) }

            is PointsListAction.OnErrorDialog ->
                uiState.update { (reducer.reduce(action, it)) }
        }
    }

    override fun handleNavigation(navigationCommand: PointsListNavigationCommand) =
        when (navigationCommand) {
            is PointsListNavigationCommand.Back -> handleNavigate(NavEvent.Back)
        }

    private fun getPoints(count: Int) {
        getPointsUseCase(count).onEach { points ->
            onIntent(PointsListIntent.UpdatePoints(points))
        }.catch {
            val error = errorHandler(it)
            onIntent(PointsListIntent.OnErrorDialog(error))
        }.launchIn(viewModelScope)
    }
}