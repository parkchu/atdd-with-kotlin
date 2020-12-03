package nextstep.subway.path.shortest.domain

class TotalValue(private val values: List<Int>) {
    fun get(type: String): List<Int> {
        return if (type == "DISTANCE") {
            values
        } else {
            values.asReversed()
        }
    }
}
