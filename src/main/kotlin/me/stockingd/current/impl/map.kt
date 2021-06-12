package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T, S> Current<T>.map(transform: (T) -> S): Current<S> = current {
    collect { emit(transform(it)) }
}
