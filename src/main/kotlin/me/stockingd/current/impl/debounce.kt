package me.stockingd.current.impl

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current

internal class CurrentDebounce<T>(private val parent: Current<T>, private val debounceTime: Long) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        var item: Maybe<T>
        var job: Job? = null
        coroutineScope {
            parent.collect {
                job?.cancel()
                item = Some(it)
                job = launch {
                    delay(debounceTime)
                    action((item as Some<T>).value)
                }
            }
        }
    }
}

fun <T> Current<T>.debounce(debounceTime: Long): Current<T> = CurrentDebounce(this, debounceTime)