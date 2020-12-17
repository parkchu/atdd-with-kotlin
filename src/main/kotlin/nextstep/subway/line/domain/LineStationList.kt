package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse

class LineStationList {
    private val mutableList = mutableListOf<LineStation>()

    fun add(lineStation: LineStation) {
        mutableList.add(lineStation)
    }

    fun map(f: (lineStation: LineStation) -> LineStationResponse): List<LineStationResponse> {
        return mutableList.map { f(it) }
    }

    fun size(): Int = mutableList.size

    fun last(): LineStation = mutableList.last()

    fun get(index: Int): LineStation {
        return mutableList[index]
    }

    fun reverse(): List<LineStation> {
        return mutableList.asReversed()
    }
}
