package nextstep.subway.path.domain

class Path(private val points: List<String>, mainValue: Int, subValue: Int, extraFare: Int = 0) {
    var mainValue: Int = mainValue
        private set
    var subValue: Int = subValue
        private set
    var extraFare: Int = extraFare
        private set

    fun add(path: Path): Path {
        val points = this.points.toMutableList()
        points.add(path.points.last())
        val extraFare = if (this.extraFare < path.extraFare) {
            path.extraFare
        } else {
            this.extraFare
        }
        return Path(points, mainValue + path.mainValue, subValue + path.subValue, extraFare)
    }

    fun updateMain(mainValue: Int) {
        this.mainValue = mainValue
    }

    fun updateSub(subValue: Int) {
        this.subValue = subValue
    }

    fun updateExtraFare(extraFare: Int) {
        this.extraFare = extraFare
    }

    fun changeMap(): Map<String, List<String>> {
        return mapOf("경로" to points, "총" to listOf(mainValue.toString(), extraFare.toString(), subValue.toString()))
    }
}
