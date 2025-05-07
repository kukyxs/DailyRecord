package com.kuky.dailyrecord.extension

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author kuky.
 * @description CoroutineExtensions
 */

inline fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.() -> Unit
) = launch(CoroutineExceptionHandler { _, _ -> } + context) { supervisorScope { block() } }

inline fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline error: suspend CoroutineScope.(CoroutineContext, Throwable) -> Unit = { _, _ -> },
    crossinline block: suspend CoroutineScope.() -> Unit
) = launch(
    CoroutineExceptionHandler { coroutineContext, throwable ->
        launch(context) { error(coroutineContext, throwable) }
    } + context
) { supervisorScope { block() } }

/**
 * Extension for delay actions by coroutine
 */
inline fun CoroutineScope.delayLaunch(
    timeMills: Long, context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    check(timeMills >= 0) { "timeMills must be positive" }
    return launch(context) {
        delay(timeMills)
        block()
    }
}

/**
 * @param interval task interval
 * @param repeatCount task repeat count
 * @param delayTime task star by delayed
 * Extension for repeat task
 */
inline fun CoroutineScope.repeatLaunch(
    interval: Long, repeatCount: Int = Int.MAX_VALUE, delayTime: Long = 0L,
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.(Int) -> Unit,
): Job {
    check(interval > 0) { "timeDelta must be large than 0" }
    check(repeatCount > 0) { "repeat count must be large than 0" }

    return launch(context) {
        if (delayTime > 0) delay(delayTime)

        repeat(repeatCount) {
            block(it)
            delay(interval)
        }
    }
}