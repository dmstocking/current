package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest

internal class TickerKtTest : DescribeSpec({
    describe("ticker") {
        it("should wait for the initial delay before ticking the time since start") {
            runTest {
                ticker(1000, 2000)
                    .take(1)
                    .toList()
                    .shouldBe(listOf(1000))
                currentTime.shouldBe(1000)
            }
        }

        it("should wait for the period delay before ticking the subsequent time since start") {
            runTest {
                ticker(1000, 2000)
                    .take(2)
                    .toList()
                    .last()
                    .shouldBe(3000)
                currentTime.shouldBe(3000)
            }
        }

        it("should be able to have no initial delay before ticking") {
            runTest {
                ticker(0, 2000)
                    .take(1)
                    .toList()
                    .shouldBe(listOf(0))
                currentTime.shouldBe(0)
            }
        }
    }
})
