package me.stockingd.current.impl

import kotlinx.coroutines.delay
import me.stockingd.current.Current

internal class CurrentTicker(private val initialDelay: Long, private val period: Long) : Current<Long> {

    override suspend fun collect(action: suspend (Long) -> Unit) {
        delay(initialDelay)
        var time = initialDelay
        action(time)
        while (true) {
            delay(period)
            time += period
            action(time)
        }
    }
}

fun ticker(initialDelay: Long, period: Long): Current<Long> = CurrentTicker(initialDelay, period)