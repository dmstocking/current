package me.stockingd.current.operators

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.Maybe
import me.stockingd.current.None
import me.stockingd.current.Some
import me.stockingd.current.current

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
