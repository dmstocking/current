package me.stockingd.current.impl

import me.stockingd.current.Current
import me.stockingd.current.withEach

fun <T> concat(vararg currents: Current<T>): Current<T> = current {
    currents.withEach {
        collect { emit(it) }
    }
}
