package me.stockingd.current

interface Current<out T> {
    suspend fun collect(action: suspend (T) -> Unit)
}
