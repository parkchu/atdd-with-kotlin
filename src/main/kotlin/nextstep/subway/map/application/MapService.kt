package nextstep.subway.map.application

import nextstep.subway.line.application.LineService
import nextstep.subway.map.dto.MapResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MapService @Autowired constructor(
        private val lineService: LineService
) {
    fun findMap(): MapResponse {
        return MapResponse.of(lineService.findAllLines())
    }
}
