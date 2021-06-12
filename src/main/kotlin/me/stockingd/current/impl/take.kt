package me.stockingd.current.impl

import kotlinx.coroutines.CancellationException
import me.stockingd.current.Current

private class AbortFlow: CancellationException()

@Suppress("SwallowedException")
fun <T> Current<T>.take(count: Int): Current<T> = current {
    var needed = count
    try {
        collect {
            if (needed > 0) {
                emit(it)
                needed--
            }

            if (needed <= 0) {
                throwExceptionToEscapeCollect()
            }
        }
    } catch (e: AbortFlow) {
        // Ignore. Used just to stop parent from emitting values.
    }
}

private fun throwExceptionToEscapeCollect() {
    throw AbortFlow()
}
