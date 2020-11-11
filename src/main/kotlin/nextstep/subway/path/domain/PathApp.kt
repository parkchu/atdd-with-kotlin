package nextstep.subway.path.domain

class PathApp : PathInterface {

    override fun getShortestPath(paths: Paths, type: String): Map<String, List<String>> {
        val pathsMap = dijkstra(paths, type)
        paths.clearVisitPoint()
        val arrivalPoint = paths.getArrivalPoint()
        return pathsMap[arrivalPoint]?.changeMap() ?: throw IllegalArgumentException("$arrivalPoint 은 존재하지 않는 포인트 입니다.")
    }

    private fun dijkstra(paths: Paths, type: String): Map<String, Path> {
        val startPoint = paths.getStartPoint()
        paths.addVisitPoint(startPoint)
        val pathsMap = paths.getPathsItToMap(startPoint).toMutableMap()
        repeat(pathsMap.size - 1) {
            val pointName = paths.getMinPoint(startPoint, pathsMap)
            paths.forEach(pointName) {
                val path = pathsMap.getValue(pointName)
                if (type == "DISTANCE") {
                    if (path.distance + it.value.distance < pathsMap.getValue(it.key).distance) {
                        pathsMap[it.key] = path.add(it.value)
                    }
                } else {
                    if (path.duration + it.value.duration < pathsMap.getValue(it.key).duration) {
                        pathsMap[it.key] = path.add(it.value)
                    }
                }

            }
        }
        return pathsMap
    }
}
