package me.stockingd.current.impl

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest

internal class MergeKtTest : DescribeSpec({
    describe("merge") {
        it("should listen to all currents concurrently") {
            runBlockingTest {
                merge<Int>(
                    current {
                        delay(1000)
                        emit(2)
                    },
                    current {
                        delay(500)
                        emit(1)
                    }
                )
                    .map { currentTime to it }
                    .toList()
                    .shouldBe(listOf(500L to 1, 1000L to 2))
            }
        }
    }
})
