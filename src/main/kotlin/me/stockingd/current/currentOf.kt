package me.stockingd.current

fun <T> currentOf(vararg items: T): Current<T> = current {
    items.forEach { emit(it) }
}
