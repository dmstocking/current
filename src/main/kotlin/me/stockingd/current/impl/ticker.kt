package me.stockingd.current.impl

import kotlinx.coroutines.delay
import me.stockingd.current.Current

fun ticker(initialDelay: Long, period: Long): Current<Long> = current {
    delay(initialDelay)
    var time = initialDelay
    emit(time)
    while (true) {
        delay(period)
        time += period
        emit(time)
    }
}
