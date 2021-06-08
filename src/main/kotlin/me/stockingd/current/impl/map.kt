package me.stockingd.current.impl

import me.stockingd.current.Current

internal class CurrentMap<T, S>(private val parent: Current<T>, private val transform: (T) -> S) : Current<S> {

    override suspend fun collect(action: suspend (S) -> Unit) {
        parent.collect { action(transform(it)) }
    }
}

fun <T, S> Current<T>.map(transform: (T) -> S): Current<S> = CurrentMap(this, transform)