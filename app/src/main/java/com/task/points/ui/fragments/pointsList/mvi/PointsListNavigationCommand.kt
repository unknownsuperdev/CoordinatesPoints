package com.task.points.ui.fragments.pointsList.mvi

import com.task.points.appbase.mvi.NavigationCommand

sealed class PointsListNavigationCommand : NavigationCommand {
    object Back : PointsListNavigationCommand()
}
