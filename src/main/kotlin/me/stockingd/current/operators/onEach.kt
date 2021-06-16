package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> Current<T>.onEach(action: suspend (T) -> Unit): Current<T> = current {
    collect { emit(it); action(it) }
}
