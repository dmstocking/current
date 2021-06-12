package me.stockingd.current.impl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current

fun <T, S> Current<T>.flatMapMerge(transform: suspend (T) -> Current<S>): Current<S> = current {
    val channel = Channel<S>()
    coroutineScope {
        launch {
            coroutineScope {
                collect { incoming ->
                    launch {
                        transform(incoming).collect { channel.send(it) }
                    }
                }
            }
            channel.close()
        }

        var result = channel.receiveCatching()
        while (result.isSuccess) {
            emit(result.getOrThrow())
            result = channel.receiveCatching()
        }
    }
}
