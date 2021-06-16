package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.Maybe
import me.stockingd.current.None
import me.stockingd.current.Some
import me.stockingd.current.current

fun <T> Current<T>.runningReduce(action: (T, T) -> T): Current<T> = current {
    var current: Maybe<T> = None()
    collect { value ->
        current.let {
            current = if (it is Some) {
                val next = action(it.value, value)
                emit(next)
                Some(next)
            } else {
                Some(value)
            }
        }
    }
}
