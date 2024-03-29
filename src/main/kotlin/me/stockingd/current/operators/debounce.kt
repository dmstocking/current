package me.stockingd.current.operators

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current
import me.stockingd.current.current
import me.stockingd.current.impl.Maybe
import me.stockingd.current.impl.Some

fun <T> Current<T>.debounce(debounceTime: Long): Current<T> = current {
    var item: Maybe<T>
    var job: Job? = null
    coroutineScope {
        collect {
            job?.cancel()
            item = Some(it)
            job = launch {
                delay(debounceTime)
                emit((item as Some<T>).value)
            }
        }
    }
}
