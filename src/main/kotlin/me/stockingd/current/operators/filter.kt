package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> Current<T>.filter(predicate: suspend (T) -> Boolean): Current<T> = current {
    collect {
        if (predicate(it)) {
            emit(it)
        }
    }
}
