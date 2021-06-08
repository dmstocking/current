package me.stockingd.current.impl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current

internal class CurrentFlatMapMerge<T, S>(
    private val parent: Current<T>,
    private val transform: suspend (T) -> Current<S>
) : Current<S> {

    override suspend fun collect(action: suspend (S) -> Unit) {
        val channel = Channel<S>()
        coroutineScope {
            launch {
                coroutineScope {
                    parent.collect { incoming ->
                        launch {
                            transform(incoming).collect { channel.send(it) }
                        }
                    }
                }
                channel.close()
            }

            var result = channel.receiveCatching()
            while (result.isSuccess) {
                action(result.getOrThrow())
                result = channel.receiveCatching()
            }
        }
    }
}

fun <T, S> Current<T>.flatMapMerge(transform: suspend (T) -> Current<S>): Current<S> = CurrentFlatMapMerge(this, transform)