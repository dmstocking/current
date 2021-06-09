package me.stockingd.current.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.stockingd.current.Current

internal class CurrentBufferCount<T>(private val parent: Current<T>, private val count: Int) : Current<List<T>> {

    override suspend fun collect(action: suspend (List<T>) -> Unit) {
        var items = mutableListOf<T>()
        parent.collect {
            items.add(it)
            if (items.size >= count) {
                action(items)
                items = mutableListOf()
            }
        }
    }
}

fun <T> Current<T>.buffer(count: Int): Current<List<T>> = CurrentBufferCount(this, count)

internal class CurrentBufferPeriod<T>(private val parent: Current<T>, private val period: Long) : Current<List<T>> {

    override suspend fun collect(action: suspend (List<T>) -> Unit) {
        var items = mutableListOf<T>()
        val mutex = Mutex()
        coroutineScope {
            val job = launch {
                while (true) {
                    delay(period)
                    mutex.withLock {
                        action(items.toList())
                        items = mutableListOf()
                    }
                }
            }
            parent.collect {
                mutex.withLock {
                    items.add(it)
                }
            }
            job.cancel()
        }
    }
}

fun <T> Current<T>.buffer(period: Long): Current<List<T>> = CurrentBufferPeriod(this, period)