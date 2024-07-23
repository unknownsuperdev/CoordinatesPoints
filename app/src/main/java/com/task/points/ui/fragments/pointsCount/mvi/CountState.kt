package com.task.points.ui.fragments.pointsCount.mvi

import com.task.points.R

data class CountState(
    val count: Int = 0,
    val countError: Int? = null
) {
    companion object {
        fun String.updateValidationAndCopy(state: CountState): CountState {
            val count = this.toIntOrNull()
            return when {
                count == null -> state.copy(
                    countError = R.string.point_count_integer
                )
                count < 0 -> state.copy(
                    countError = R.string.point_count_positive
                )
                else -> state.copy(
                    count = count,
                    countError = null
                )
            }
        }
    }
}