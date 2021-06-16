package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> Current<T>.repeat(): Current<T> = current {
    val values = mutableListOf<T>()
    collect {
        values.add(it)
        emit(it)
    }
    while (true) {
        values.forEach { emit(it) }
    }
}
