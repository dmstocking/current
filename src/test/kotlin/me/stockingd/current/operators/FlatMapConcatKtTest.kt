package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import me.stockingd.current.current
import me.stockingd.current.currentOf

internal class FlatMapConcatKtTest : DescribeSpec({
    describe("map") {
        it("should collect each flow in order") {
            currentOf(1, 2, 3)
                .flatMapConcat { value ->
                    current {
                        repeat(value) {
                            emit(value)
                        }
                    }
                }
                .toList()
                .shouldBe(listOf(1, 2, 2, 3, 3, 3))
        }

        it("should not collect in parallel") {
            currentOf(1, 2, 3)
                .flatMapConcat { value ->
                    current {
                        delay(3L - value)
                        emit(value)
                    }
                }
                .toList()
                .shouldBe(listOf(1,  2, 3))
        }
    }
})
