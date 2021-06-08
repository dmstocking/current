package me.stockingd.current.impl

import me.stockingd.current.Current

internal class CurrentFlatMapConcat<T, S>(
    private val parent: Current<T>,
    private val transform: suspend (T) -> Current<S>
) : Current<S> {

    override suspend fun collect(action: suspend (S) -> Unit) {
        parent.collect { incoming ->
            transform(incoming).collect { action(it) }
        }
    }
}

fun <T, S> Current<T>.flatMapConcat(transform: suspend (T) -> Current<S>): Current<S> = CurrentFlatMapConcat(this, transform)