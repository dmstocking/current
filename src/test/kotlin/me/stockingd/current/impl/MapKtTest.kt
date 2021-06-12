package me.stockingd.current.impl

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runBlockingTest

internal class MapKtTest : DescribeSpec({
    describe("map") {
        it("should map the value and emit it") {
            currentOf(1, 2, 3)
                .map { it.toString() }
                .toList()
                .shouldBe(listOf("1", "2", "3"))
        }

        it("should not introduce delays") {
            runBlockingTest {
                currentOf(1, 2, 3)
                    .map { it.toString() }
                    .toList()
                currentTime.shouldBe(0)
            }
        }
    }
})
