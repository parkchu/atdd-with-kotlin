package nextstep.subway.path.domain

class PathApp : PathInterface {

    override fun getShortestPath(paths: Paths): Map<String, List<String>> {
        val pathsMap = dijkstra(paths)
        paths.clearVisitPoint()
        val arrivalPoint = paths.getArrivalPoint()
        return pathsMap[arrivalPoint]?.changeMap() ?: throw IllegalArgumentException("$arrivalPoint 은 존재하지 않는 포인트 입니다.")
    }

    private fun dijkstra(paths: Paths): Map<String, Path> {
        val startPoint = paths.getStartPoint()
        paths.addVisitPoint(startPoint)
        val pathsMap = paths.getPathsItToMap(startPoint).toMutableMap()
        repeatSetPathsMap(paths, pathsMap)
        return pathsMap
    }

    private fun repeatSetPathsMap(paths: Paths, pathsMap: MutableMap<String, Path>) {
        repeat(pathsMap.size - 1) { loopMinPointPaths(paths, pathsMap) }
    }

    private fun loopMinPointPaths(paths: Paths, pathsMap: MutableMap<String, Path>) {
        val pointName = paths.getMinPoint(paths.getStartPoint(), pathsMap)
        paths.forEach(pointName) { setPathsMap(pathsMap, it, pointName) }
    }

    private fun setPathsMap(pathsMap: MutableMap<String, Path>, it: Map.Entry<String, Path>, pointName: String) {
        val path = pathsMap.getValue(pointName)
        if (path.mainValue + it.value.mainValue < pathsMap.getValue(it.key).mainValue) {
            pathsMap[it.key] = path.add(it.value)
        }
    }
}
