package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class RangeKtTest : DescribeSpec({
    describe("range") {
        it("should provide the values from start to end-1") {
            range(0, 10)
                .toList()
                .shouldBe(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
        }
    }
})
