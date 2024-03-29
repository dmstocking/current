package me.stockingd.current.operators

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.current
import me.stockingd.current.impl.Maybe
import me.stockingd.current.impl.None
import me.stockingd.current.impl.Some

fun <T, S> combine(currents: List<Current<T>>, transform: suspend (List<T>) -> S): Current<S> = current {
    val channel = Channel<Pair<Int, T>>()
    val mutableList = MutableList<Maybe<T>>(currents.size) { None() }
    coroutineScope {
        launch {
            coroutineScope {
                currents.forEachIndexed { index, current ->
                    launch {
                        current.collect { channel.send(index to it) }
                    }
                }
            }
            channel.close()
        }

        var result = channel.receiveCatching()
        while (result.isSuccess) {
            val (index, value) = result.getOrThrow()
            mutableList[index] = Some(value)
            if (mutableList.all { it is Some }) {
                emit(transform(mutableList.map { (it as Some<T>).value }))
            }
            result = channel.receiveCatching()
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, S> combine(c1: Current<T1>, c2: Current<T2>, transform: suspend (T1, T2) -> S): Current<S> =
    combine(listOf(c1, c2)) { (v1, v2) -> transform(v1 as T1, v2 as T2) }
