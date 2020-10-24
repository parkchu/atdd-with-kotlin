package nextstep.subway.path.ui

import nextstep.subway.path.dto.PathResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/paths")
class PathController {
    @GetMapping
    fun findShortestPath(@RequestParam("source") startStationId: Long, @RequestParam("target") arrivalStationId: Long): ResponseEntity<PathResponse> {
        return ResponseEntity.ok().body(PathResponse(listOf(), 10, 10))
    }
}
