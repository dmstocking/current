package me.stockingd.current.operators

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import me.stockingd.current.currentOf

internal class ChunkKtTest : DescribeSpec({

    describe("chunk") {
        it("should group each n items into a list") {
            currentOf(1, 2, 3, 4, 5)
                .chunk(2)
                .toList()
                .shouldBe(listOf(
                    listOf(1, 2),
                    listOf(3, 4),
                    listOf(5),
                ))
        }

        it("should introduce no delays") {
            runTest {
                currentOf(1, 2, 3, 4, 5)
                    .chunk(2)
                    .toList()
                currentTime.shouldBe(0)
            }
        }
    }

    describe("chunkFixedDelay") {
        it("should group items by time into lists") {
            runTest {
                currentOf(1, 2, 3, 4, 5)
                    .map {
                        delay(100L * it)
                        it
                    }
                    .chunkFixedDelay(1000L)
                    .toList()
                    .shouldBe(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5),
                    ))
            }
        }
    }
})
