package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import me.stockingd.current.currentOf

internal class RepeatKtTest : DescribeSpec({
    describe("repeat") {
        it("should repeat the current after it completes") {
            currentOf(1, 2, 3)
                .repeat()
                .take(10)
                .toList()
                .shouldBe(listOf(1, 2, 3, 1, 2, 3, 1, 2, 3, 1))
        }
    }
})
