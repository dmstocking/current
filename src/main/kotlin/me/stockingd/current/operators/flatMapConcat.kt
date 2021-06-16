package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T, S> Current<T>.flatMapConcat(transform: suspend (T) -> Current<S>): Current<S> = current {
    collect { incoming ->
        transform(incoming).collect { emit(it) }
    }
}
