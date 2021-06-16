package me.stockingd.current.operators

import kotlinx.coroutines.delay
import me.stockingd.current.Current
import me.stockingd.current.current

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
