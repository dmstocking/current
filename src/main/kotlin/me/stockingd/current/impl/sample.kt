package me.stockingd.current.impl

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.stockingd.current.Current

internal class CurrentSample<T>(private val parent: Current<T>, private val period: Long) : Current<T> {

    override suspend fun collect(action: suspend (T) -> Unit) {
        var item: Maybe<T> = None()
        coroutineScope {
            val job = launch {
                while (true) {
                    delay(period)
                    (item as? Some<T>)?.let {
                        action(it.value)
                        item = None()
                    }
                }
            }
            parent.collect { item = Some(it) }
            job.cancel()
        }
    }
}

fun <T> Current<T>.sample(period: Long): Current<T> = CurrentSample(this, period)