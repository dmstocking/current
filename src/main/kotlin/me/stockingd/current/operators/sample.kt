package me.stockingd.current.operators

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.current
import me.stockingd.current.impl.Maybe
import me.stockingd.current.impl.None
import me.stockingd.current.impl.Some

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
