package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import me.stockingd.current.currentOf

internal class RunningReduceKtTest : DescribeSpec({
    describe("runningReduce") {
        it("should reduce the entire current returning every time") {
            currentOf(1, 2, 3, 4)
                .runningReduce { a, b -> a + b }
                .toList()
                .shouldBe(listOf(3, 6, 10))
        }
    }
})
