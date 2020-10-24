package nextstep.subway.path.ui

import nextstep.subway.path.dto.PathResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/paths")
class PathController {
    @GetMapping
    fun findShortestPath(): ResponseEntity<PathResponse> {
        return ResponseEntity.ok().body(PathResponse())
    }
}
