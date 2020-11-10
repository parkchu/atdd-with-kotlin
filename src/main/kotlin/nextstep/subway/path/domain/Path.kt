package nextstep.subway.path.domain

class Path(private val points: List<String>, distance: Int, duration: Int) {
    var distance: Int = distance
        private set
    var duration: Int = duration
        private set

    fun add(path: Path): Path {
        val points = this.points.toMutableList()
        points.add(path.points.last())
        return Path(points, distance + path.distance, duration + path.duration)
    }

    fun updateDistance(distance: Int) {
        this.distance = distance
    }

    fun updateDuration(duration: Int) {
        this.duration = duration
    }

    fun changeMap(): Map<String, List<String>> {
        return mapOf("경로" to points, "총" to listOf(distance.toString(), duration.toString()))
    }
}
