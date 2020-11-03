package nextstep.subway.path.domain

class Path(val stations: List<PathStation>) {
    val distance: Int
        get() = stations.sumBy { it.distance }
    val duration: Int
        get() = stations.sumBy { it.duration }
}
