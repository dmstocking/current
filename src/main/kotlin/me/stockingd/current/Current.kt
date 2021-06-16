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
