package me.stockingd.current.impl

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.be
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*

internal class MergeKtTest : DescribeSpec({
    describe("merge") {
        it("should listen to all currents concurrently") {
            runBlockingTest {
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
                    .shouldBe(listOf(500L to 1, 1000L to 2))
            }
        }
    }
})
