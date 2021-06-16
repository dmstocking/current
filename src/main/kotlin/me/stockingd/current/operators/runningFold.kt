package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T, S> Current<T>.runningFold(initialValue: S, action: (S, T) -> S): Current<S> = current {
    var current = initialValue
    collect {
        current = action(current, it)
        emit(current)
    }
}
