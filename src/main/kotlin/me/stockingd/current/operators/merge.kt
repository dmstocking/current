package me.stockingd.current.operators

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> merge(vararg currents: Current<T>): Current<T> = current {
    val channel = Channel<T>()
    coroutineScope {
        launch {
            coroutineScope {
                currents.forEach {
                    launch {
                        it.collect { channel.send(it) }
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
