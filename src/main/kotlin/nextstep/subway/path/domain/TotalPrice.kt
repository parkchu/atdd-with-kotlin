package nextstep.subway.path.domain


object TotalPrice {
    fun get(distance: Int, extraFare: Int = 0, age: Int = 20): Int {
        val totalPrice = calculatePriceOfDistance(distance) + extraFare
        return calculatePriceOfAge(totalPrice, age)
    }

    private fun calculatePriceOfAge(price: Int, age: Int): Int {
        var totalPrice = price
        if (age in 6..12) {
            totalPrice /= 2
        }
        if (age in 13..18) {
            totalPrice = totalPrice * 4 / 5
        }
        return totalPrice + 350
    }

    private fun calculatePriceOfDistance(distance: Int): Int {
        var price = BASIC_PRICE
        if (distance in 11..50) {
            price += (((distance - 11) / 5) + 1) * ADD_PRICE
        }
        if (distance > 50) {
            price += OVER_50KM_BASIC_PRICE + (((distance - 51) / 8) + 1) * ADD_PRICE
        }
        return price
    }

    private const val BASIC_PRICE = 900
    private const val ADD_PRICE = 100
    private const val OVER_50KM_BASIC_PRICE = 800
}
