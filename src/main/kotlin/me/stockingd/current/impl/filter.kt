package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T> Current<T>.filter(predicate: suspend (T) -> Boolean): Current<T> = current {
    collect {
        if (predicate(it)) {
            emit(it)
        }
    }
}
