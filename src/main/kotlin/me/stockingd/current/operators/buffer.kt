package me.stockingd.current.operators

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> Current<T>.buffer(count: Int): Current<T> = current {
    val channel = Channel<T>(capacity = count)
    coroutineScope {
        launch {
            var result = channel.receiveCatching()
            while (result.isSuccess) {
                emit(result.getOrThrow())
                result = channel.receiveCatching()
            }
        }

        collect {
            channel.send(it)
        }
        channel.close()
    }
}
