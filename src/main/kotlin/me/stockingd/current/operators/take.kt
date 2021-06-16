package me.stockingd.current.operators

import kotlinx.coroutines.CancellationException
import me.stockingd.current.Current
import me.stockingd.current.current

private class AbortFlow : CancellationException()

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
