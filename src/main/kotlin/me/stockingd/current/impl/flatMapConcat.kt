package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T, S> Current<T>.flatMapConcat(transform: suspend (T) -> Current<S>): Current<S> = current {
    collect { incoming ->
        transform(incoming).collect { emit(it) }
    }
}
