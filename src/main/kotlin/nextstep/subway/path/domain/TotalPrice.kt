package nextstep.subway.path.domain


object TotalPrice {
    fun get(distance: Int): Int {
        return calculatePrice(distance)
    }

    private fun calculatePrice(distance: Int): Int {
        var price = BASIC_PRICE
        if (distance in 11..50) {
            price += (((distance - 11) / 5) + 1) * ADD_PRICE
        }
        if (distance > 50) {
            price += OVER_50KM_BASIC_PRICE + (((distance - 51) / 8) + 1) * ADD_PRICE
        }
        return price
    }

    private const val BASIC_PRICE = 1250
    private const val ADD_PRICE = 100
    private const val OVER_50KM_BASIC_PRICE = 800
}
