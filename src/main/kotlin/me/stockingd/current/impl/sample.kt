package me.stockingd.current.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current

fun <T> Current<T>.sample(period: Long): Current<T> = current {
    var item: Maybe<T> = None()
    coroutineScope {
        val job = launch {
            while (true) {
                delay(period)
                (item as? Some<T>)?.let {
                    emit(it.value)
                    item = None()
                }
            }
        }
        collect { item = Some(it) }
        job.cancel()
    }
}
