package nextstep.subway.path.domain

class PathApp : PathInterface {

    override fun getShortestPath(paths: Paths, startPoint: String, arrivalPoint: String): Map<String, List<String>> {
        val pathsMap = dijkstra(paths, startPoint)
        return pathsMap[arrivalPoint]?.changeMap() ?: throw IllegalArgumentException("$arrivalPoint 은 존재하지 않는 포인트 입니다.")
    }

    private fun dijkstra(paths: Paths, startPoint: String): Map<String, Path> {
        paths.addVisitPoint(startPoint)
        val pathsMap = paths.getPathsItToMap(startPoint).toMutableMap()
        repeat(pathsMap.size - 1) {
            val pointName = paths.getMinStationId2(startPoint, pathsMap)
            paths.addVisitPoint(pointName)
            val pathsMap2 = paths.getPathsItToMap(pointName)
            pathsMap2.forEach {
                val path = pathsMap.getValue(pointName)
                if (path.distance + it.value.distance < pathsMap.getValue(it.key).distance) {
                    pathsMap[it.key] = path.add(it.value)
                }
            }
        }
        return pathsMap
    }
}
