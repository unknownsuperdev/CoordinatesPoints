package com.task.points.appbase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.points.appbase.mvi.*
import com.task.points.appbase.navigation.NavEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviBaseViewModel<  S : State, A : Action,I : Intent, NC: NavigationCommand> :
    ViewModel() {
    protected val uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    val getUiState: StateFlow<S> = uiState

    private val navEvents = MutableSharedFlow<NavEvent>()
    val getNavEvents: Flow<NavEvent> = navEvents

    abstract fun initState(): S

    abstract fun intentToAction(intent: I): A

    abstract fun handleAction(action: A)

    abstract fun handleNavigation(navigationCommand: NC)

    fun onIntent(intent: I) {
        val action = intentToAction(intent)
        handleAction(action)
    }

    fun handleNavigate(destination: NavEvent){
        viewModelScope.launch {
            navEvents.emit(destination)
        }
    }

    protected open fun onNavigate(navigationCommand: NC) {
        handleNavigation(navigationCommand)
    }

}
