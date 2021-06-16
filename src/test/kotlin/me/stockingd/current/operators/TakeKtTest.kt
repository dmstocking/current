package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runBlockingTest
import me.stockingd.current.currentOf

class TakeKtTest : DescribeSpec({
    describe("take") {
        it("should take the number of values then not take anymore") {
            runBlockingTest {
                currentOf(1, 2, 3, 4)
                    .take(2)
                    .toList()
                    .shouldBe(listOf(1, 2))
            }
        }

        it("should not introduce any delays") {
            runBlockingTest {
                currentOf(1, 2, 3, 4)
                    .take(2)
                    .toList()
                currentTime.shouldBe(0)
            }
        }
    }
})
