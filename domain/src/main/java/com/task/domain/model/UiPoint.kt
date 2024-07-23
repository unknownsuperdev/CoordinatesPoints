package com.task.domain.model

data class UiPoint(
    val x: Double,
    val y: Double
): DiffUtilModel<Int>() {
    override val id: Int
        get() = this.hashCode()
}
