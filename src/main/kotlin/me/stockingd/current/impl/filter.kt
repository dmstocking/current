package me.stockingd.current.impl

import me.stockingd.current.Current

internal class CurrentFilter<T>(private val parent: Current<T>, private val predicate: suspend (T) -> Boolean) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        parent.collect {
            if (predicate(it)) {
                action(it)
            }
        }
    }
}

fun <T> Current<T>.filter(predicate: suspend (T) -> Boolean): Current<T> = CurrentFilter(this, predicate)