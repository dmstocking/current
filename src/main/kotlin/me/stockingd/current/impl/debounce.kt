package me.stockingd.current.impl

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current

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
