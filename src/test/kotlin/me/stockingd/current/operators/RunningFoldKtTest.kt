package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import me.stockingd.current.currentOf

internal class RunningFoldKtTest : DescribeSpec({
    describe("runningFold") {
        it("should fold over the entire current emitting the value every time") {
            currentOf(1, 2, 3, 4)
                .runningFold(0) { a, b -> a + b }
                .toList()
                .shouldBe(listOf(1, 3, 6, 10))
        }
    }
})
