package me.stockingd.current.operators

import me.stockingd.current.Current

suspend fun <T> Current<T>.toList(): List<T> {
    return buildList {
        collect { add(it) }
    }
}
