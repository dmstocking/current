package me.stockingd.current.impl

import me.stockingd.current.Current

internal class CurrentOnEach<T>(private val parent: Current<T>, private val action: suspend (T) -> Unit) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        parent.collect { this.action(it); action(it) }
    }
}

fun <T> Current<T>.onEach(action: suspend (T) -> Unit): Current<T> = CurrentOnEach(this, action)