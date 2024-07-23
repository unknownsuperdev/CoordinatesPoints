package com.task.points.appbase.mvi

interface Reducer<A : Action, S : State> {

    fun reduce(action: A, state: S): S
}
