import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import me.stockingd.current.Current
import me.stockingd.current.impl.*

suspend fun main() {
    println("Running....")
    currentOf(1, 2, 3, 4)
        .map { it.toString() }
        .flatMapMerge {
            delay(1_000)
            currentOf(it)
        }
        .collect { println(it) }
    println("Done")

    current<Int> {
        emit(1)
        delay(1_000)
        emit(1)
        delay(1_000)
        emit(1)
        emit(1)
        emit(1)
        delay(1_000)
        emit(1)
    }
        .collect { println(it) }

    val flow = currentOf(1, 2).onEach { delay(10) }
    val flow2 = currentOf("a", "b", "c").onEach { delay(15) }
    combine(flow, flow2) { i, s -> i.toString() + s }
        .filter { !it.endsWith("b") }
        .collect { println(it) /* Will print "1a 2a 2c" */ }


    ticker(1000, 100)
        .take(10)
        .collect { println(it) }

    coroutineScope {
        val job = ticker(1000, 100)
            .take(10)
            .onEach { println(it) }
            .launchIn(this)

        delay(1500)
        job.cancel()
    }
}