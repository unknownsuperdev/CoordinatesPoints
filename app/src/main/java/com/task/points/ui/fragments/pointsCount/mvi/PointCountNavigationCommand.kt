package com.task.points.ui.fragments.pointsCount.mvi

import com.task.points.appbase.mvi.NavigationCommand

sealed class PointCountNavigationCommand : NavigationCommand {
    object Close : PointCountNavigationCommand()
    data class GoToPointsList(val count: Int) : PointCountNavigationCommand()
}
