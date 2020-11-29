package nextstep.subway.path.domain

import nextstep.subway.path.domain.Age.CHILD
import nextstep.subway.path.domain.Age.TEENAGER
import nextstep.subway.path.domain.Distance.OVER_10KM
import nextstep.subway.path.domain.Distance.OVER_50KM

object TotalPrice {
    fun get(distance: Int, extraFare: Int = BASIC_EXTRA_FARE, age: Int = BASIC_AGE): Int {
        val totalPrice = calculatePriceOfDistance(distance) + extraFare
        return calculatePriceOfAge(totalPrice, age)
    }

    private fun calculatePriceOfAge(price: Int, age: Int): Int {
        val totalPrice = when {
            CHILD.itIn(age) -> {
                CHILD.getPrice(price)
            }
            TEENAGER.itIn(age) -> {
                TEENAGER.getPrice(price)
            }
            else -> {
                price
            }
        }
        return totalPrice + Price.OUT.value
    }

    private fun calculatePriceOfDistance(distance: Int): Int {
        return when {
            OVER_10KM.itIn(distance) -> {
                OVER_10KM.getPrice(distance)
            }
            OVER_50KM.itIn(distance) -> {
                OVER_50KM.getPrice(distance) + Price.OVER_50KM_BASIC.value
            }
            else -> {
                Price.BASIC.value
            }
        }
    }

    private const val BASIC_AGE = 20
    private const val BASIC_EXTRA_FARE = 0
}
