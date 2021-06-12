package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T> currentOf(vararg items: T): Current<T> = current {
    items.forEach { emit(it) }
}
