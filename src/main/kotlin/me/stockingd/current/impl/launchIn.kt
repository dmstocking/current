package me.stockingd.current.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.stockingd.current.Current

fun <T> Current<T>.launchIn(scope: CoroutineScope): Job = scope.launch {
    collect { }
}
