package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runBlockingTest
import me.stockingd.current.currentOf

internal class OnEachKtTest : DescribeSpec({
    describe("onEach") {
        it("should perform the action the emit the value") {
            currentOf(1, 2, 3)
                .toList()
                .shouldBe(listOf(1, 2, 3))
        }

        it("should not introduce delays") {
            runBlockingTest {
                currentOf(1, 2, 3)
                    .collect { }
                currentTime.shouldBe(0)
            }
        }
    }
})
