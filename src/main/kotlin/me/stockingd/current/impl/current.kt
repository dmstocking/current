package me.stockingd.current.impl

import me.stockingd.current.Current

fun <T> current(builder: suspend CurrentEmitter<T>.() -> Unit): Current<T> = object : Current<T> {
    override suspend fun collect(action: suspend (T) -> Unit) {
        val emitter = object : CurrentEmitter<T> {
            override suspend fun emit(value: T) {
                action(value)
            }
        }
        emitter.builder()
    }
}

interface CurrentEmitter<T> {
    suspend fun emit(value: T)
}
