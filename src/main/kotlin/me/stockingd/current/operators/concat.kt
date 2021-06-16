package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current
import me.stockingd.current.withEach

fun <T> concat(vararg currents: Current<T>): Current<T> = current {
    currents.withEach {
        collect { emit(it) }
    }
}
