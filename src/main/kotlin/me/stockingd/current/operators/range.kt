package me.stockingd.current.operators

import me.stockingd.current.Current
import me.stockingd.current.current

fun range(start: Int, end: Int): Current<Int> = current {
    (start until end).forEach { emit(it) }
}
