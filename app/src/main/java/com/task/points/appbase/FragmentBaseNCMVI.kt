package com.task.points.appbase

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.task.points.appbase.mvi.NavigationCommand
import com.task.points.appbase.mvi.Action
import com.task.points.appbase.mvi.Intent
import com.task.points.appbase.mvi.State
import com.task.points.appbase.navigation.NavEvent
import com.task.points.appbase.viewmodel.MviBaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class FragmentBaseNCMVI<
        S : State,
        A : Action,
        I : Intent,
        NC : NavigationCommand,
        VM : MviBaseViewModel<S, A, I, NC>,
        VB : ViewBinding> : com.task.points.appbase.FragmentBaseMVI<S, A, I, NC, VM, VB>() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        collectNavigationEvents()
    }

    private fun collectNavigationEvents() {
        viewModel.getNavEvents.onEach {
            when (it) {
                is NavEvent.Back -> {
                    popBackStack()
                }
                is NavEvent.Destination -> {
                    navigate(it.destination)
                }
//                is NavEvent.DeepLink -> {
//                    val request = NavDeepLinkRequest.Builder.fromUri(it.uri).build()
//                    navigate(request)
//                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    private fun navigate(destinationId: Int, arg: Bundle? = null) {
        navController.navigate(destinationId, arg)
    }

    private fun navigate(destinations: NavDirections) {
        navController.navigate(destinations)
    }

    private fun navigate(deepLinkRequest: NavDeepLinkRequest) {
        navController.navigate(deepLinkRequest)
    }
}
