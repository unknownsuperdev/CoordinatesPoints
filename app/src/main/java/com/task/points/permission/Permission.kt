package com.task.points.permission

data class Permission(
    val permission: String,
    val isGranted: Boolean,
    val shouldShowRational: Boolean = false
)
