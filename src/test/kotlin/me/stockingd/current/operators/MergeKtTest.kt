package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import me.stockingd.current.current

internal class MergeKtTest : DescribeSpec({
    describe("merge") {
        it("should listen to all currents concurrently") {
            runTest {
                merge(
                    current<Int> {
                        delay(1000)
                        emit(2)
                    },
                    current<Int> {
                        delay(500)
                        emit(1)
                    }
                )
                    .map { currentTime to it }
                    .toList()
                    .shouldContainExactly(
                        500L to 1,
                        1000L to 2
                    )
            }
        }
    }
})
