package me.stockingd.current.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.stockingd.current.Current

fun <T> Current<T>.buffer(count: Int): Current<List<T>> = current {
    var items = mutableListOf<T>()
    collect {
        items.add(it)
        if (items.size >= count) {
            emit(items)
            items = mutableListOf()
        }
    }
}

fun <T> Current<T>.buffer(period: Long): Current<List<T>> = current {
    var items = mutableListOf<T>()
    val mutex = Mutex()
    coroutineScope {
        val job = launch {
            while (true) {
                delay(period)
                mutex.withLock {
                    emit(items.toList())
                    items = mutableListOf()
                }
            }
        }
        collect {
            mutex.withLock {
                items.add(it)
            }
        }
        job.cancel()
    }
}
