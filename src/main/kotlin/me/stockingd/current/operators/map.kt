package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T, S> Current<T>.map(transform: suspend (T) -> S): Current<S> = current {
    collect { emit(transform(it)) }
}
