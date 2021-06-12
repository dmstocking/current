package me.stockingd.current.impl

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runBlockingTest

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
            runBlockingTest {
                currentOf(1, 2, 3)
                    .onEach { println(it.toString()) }
                    .collect { }
                currentTime.shouldBe(0)
            }
        }
    }
})
