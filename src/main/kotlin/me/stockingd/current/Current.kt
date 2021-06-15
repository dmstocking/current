package me.stockingd.current

interface Current<out T> {
    suspend fun collect(action: suspend (T) -> Unit)
}

suspend fun <T> Current<T>.collectIndexed(action: suspend (Int, T) -> Unit) {
    var i = 0
    collect {
        action(i++, it)
    }
}
