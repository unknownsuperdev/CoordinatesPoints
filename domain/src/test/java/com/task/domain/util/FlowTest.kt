package com.task.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.testFirst(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    block: T.() -> Unit
) {
    runTest (coroutineContext) { block.invoke(first()) }
}

fun <T> Flow<T>.assertEquals(
    actual: T,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) {
    testFirst(coroutineContext) { assertEquals(this, actual) }
}

