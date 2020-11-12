package nextstep.subway.path.domain

class Path(private val points: List<String>, mainValue: Int, subValue: Int) {
    var mainValue: Int = mainValue
        private set
    var subValue: Int = subValue
        private set

    fun add(path: Path): Path {
        val points = this.points.toMutableList()
        points.add(path.points.last())
        return Path(points, mainValue + path.mainValue, subValue + path.subValue)
    }

    fun updateMain(mainValue: Int) {
        this.mainValue = mainValue
    }

    fun updateSub(subValue: Int) {
        this.subValue = subValue
    }

    fun changeMap(): Map<String, List<String>> {
        return mapOf("경로" to points, "총" to listOf(mainValue.toString(), subValue.toString()))
    }
}
