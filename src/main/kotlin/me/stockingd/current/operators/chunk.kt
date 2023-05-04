package me.stockingd.current.operators

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.stockingd.current.Current
import me.stockingd.current.current

fun <T> Current<T>.chunk(count: Int): Current<List<T>> = current {
    var items = mutableListOf<T>()
    collect {
        items.add(it)
        if (items.size >= count) {
            emit(items)
            items = mutableListOf()
        }
    }
    if (items.isNotEmpty()) {
        emit(items)
    }
}

fun <T> Current<T>.chunkFixedDelay(fixedDelay: Long): Current<List<T>> = current {
    var items = mutableListOf<T>()
    val mutex = Mutex()
    coroutineScope {
        val job = launch {
            while (true) {
                delay(fixedDelay)
                mutex.withLock {
                    emit(items.toList())
                    items = mutableListOf()
                }
            }
        }
        try {
            collect {
                mutex.withLock {
                    items.add(it)
                }
            }
        } finally {
            job.cancel()
        }

        if (items.isNotEmpty()) {
            emit(items.toList())
        }
    }
}
