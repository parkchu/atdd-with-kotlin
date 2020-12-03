package nextstep.subway.path.shortest.domain

enum class Age(private val range: IntRange, private val percent: Double) {
    CHILD(6..12, 0.5),
    TEENAGER(13..18, 0.8);

    fun itIn(age: Int): Boolean {
        return age in range
    }

    fun getPrice(price: Int): Int {
        return (price * percent).toInt()
    }
}
