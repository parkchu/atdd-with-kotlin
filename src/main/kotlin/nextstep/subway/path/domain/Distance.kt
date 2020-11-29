package nextstep.subway.path.domain

import nextstep.subway.path.domain.Paths.Companion.INF

enum class Distance(private val range: IntRange, private val minusKm: Int, private val standard: Int) {
    OVER_10KM(11..50, 6, 5),
    OVER_50KM(50..INF, 43, 8);

    fun itIn(distance: Int): Boolean {
        return distance in range
    }

    fun getPrice(distance: Int): Int {
        return (distance - minusKm) / standard * Price.ADD.value + Price.BASIC.value
    }
}
