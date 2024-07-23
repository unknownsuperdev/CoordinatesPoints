package com.task.points.extensions

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.dpToPx(id: Int): Int = resources.getDimensionPixelSize(id)

fun <T> debounce(
    scope: CoroutineScope,
    delay: Long = 300L,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delay)
            destinationFunction(param)
        }
    }
}
