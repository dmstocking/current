package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import me.stockingd.current.currentOf

internal class MapKtTest : DescribeSpec({
    describe("map") {
        it("should map the value and emit it") {
            currentOf(1, 2, 3)
                .map { it.toString() }
                .toList()
                .shouldBe(listOf("1", "2", "3"))
        }

        it("should not introduce delays") {
            runTest {
                currentOf(1, 2, 3)
                    .map { it.toString() }
                    .toList()
                currentTime.shouldBe(0)
            }
        }
    }
})
