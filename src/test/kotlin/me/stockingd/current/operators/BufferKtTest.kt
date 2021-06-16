package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import me.stockingd.current.current
import me.stockingd.current.currentOf

internal class BufferKtTest : DescribeSpec({

    describe("buffer") {
        it("should create a new buffer on the first item and every skip") {
            runBlockingTest {
                val connectionStatus = current<String> {
                    delay(100)
                    emit("b")
                    delay(1000)
                    emit("c")
                    delay(100)
                    emit("d")
                }

                concat(
                    currentOf("a"),
                    connectionStatus
                )
//              "a 100ms b 1s c 100ms d"
                    .buffer(2, 1)
                    .map { currentTime to it }
                    .toList()
                    .shouldBe(listOf(
                        100L to listOf("a", "b"),
                        1100L to listOf("b", "c"),
                        1200L to listOf("c", "d")
                    ))
            }
        }
    }
})
