package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T> Current<T>.onEach(action: suspend (T) -> Unit): Current<T> = current {
    collect { emit(it); action(it) }
}
