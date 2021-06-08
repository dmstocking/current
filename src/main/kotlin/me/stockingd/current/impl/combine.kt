package me.stockingd.current.impl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import java.util.concurrent.atomic.AtomicInteger

internal sealed class Maybe<T>
internal class Some<T>(val value: T) : Maybe<T>()
internal class None<T> : Maybe<T>()


internal class CurrentCombine<T, S>(
    private val currents: List<Current<T>>,
    private val transform: suspend (List<T>) -> S
) : Current<S> {

    override suspend fun collect(action: suspend (S) -> Unit) {
        val channel = Channel<Pair<Int, T>>()
        val mutableList = MutableList<Maybe<T>>(currents.size) { None() }
        val counter = AtomicInteger(currents.size)
        coroutineScope {
            launch {
                coroutineScope {
                    currents.forEachIndexed { index, current ->
                        launch {
                            current.collect { channel.send(index to it) }
                            if (counter.decrementAndGet() == 0) {
                                channel.close()
                            }
                        }
                    }
                }
            }

            var result = channel.receiveCatching()
            while (result.isSuccess) {
                val (index, value) = result.getOrThrow()
                mutableList[index] = Some(value)
                if (mutableList.all { it is Some }) {
                    action(transform(mutableList.map { (it as Some<T>).value }))
                }
                result = channel.receiveCatching()
            }
        }
    }
}

fun <T, S> combine(currents: List<Current<T>>, transform: suspend (List<T>) -> S): Current<S> =
    CurrentCombine(currents, transform)

@Suppress("UNCHECKED_CAST")
fun <T1, T2, S> combine(c1: Current<T1>, c2: Current<T2>, transform: suspend (T1, T2) -> S): Current<S> =
    CurrentCombine(listOf(c1, c2)) { (v1, v2) -> transform(v1 as T1, v2 as T2) }
