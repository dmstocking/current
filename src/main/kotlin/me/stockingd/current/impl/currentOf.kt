package me.stockingd.current.impl

import me.stockingd.current.Current

internal class CurrentOf<T>(private val items: List<T>) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        items.forEach { action(it) }
    }
}

fun <T> currentOf(vararg items: T): Current<T> = CurrentOf(items.toList())