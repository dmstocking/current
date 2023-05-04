package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import me.stockingd.current.currentOf

internal class OnEachKtTest : DescribeSpec({
    describe("onEach") {
        it("should perform the action the emit the value") {
            buildList {
                currentOf(1, 2, 3)
                    .onEach { add(it) }
                    .collect { }
            }.shouldBe(listOf(1, 2, 3))
        }

        it("should not introduce delays") {
            runTest {
                currentOf(1, 2, 3)
                    .collect { }
                currentTime.shouldBe(0)
            }
        }
    }
})
