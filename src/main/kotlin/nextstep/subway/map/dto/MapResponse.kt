package nextstep.subway.map.dto

import nextstep.subway.line.dto.LineResponse

class MapResponse (
    val lineResponses: List<LineResponse>
) {
    companion object {
        fun of(lines: List<LineResponse> = listOf()): MapResponse {
            return MapResponse(lines)
        }
    }
}
