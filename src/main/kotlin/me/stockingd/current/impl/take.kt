package me.stockingd.current.impl

import kotlinx.coroutines.CancellationException
import me.stockingd.current.Current

private class AbortFlow: CancellationException()

internal class CurrentTake<T>(private val parent: Current<T>, private val count: Int) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        var count = this.count
        try {
            parent.collect {
                if (count > 0) {
                    action(it)
                    count--
                }

                if (count <= 0) {
                    throwExceptionToEscapeCollect()
                }
            }
        } catch (e: AbortFlow) {
            // Ignore. Used just to stop parent from emitting values.
        }
    }
}

private fun throwExceptionToEscapeCollect() {
    throw AbortFlow()
}

fun <T> Current<T>.take(count: Int): Current<T> = CurrentTake(this, count)