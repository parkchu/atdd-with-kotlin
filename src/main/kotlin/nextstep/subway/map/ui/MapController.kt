package nextstep.subway.map.ui

import nextstep.subway.map.application.MapService
import nextstep.subway.map.dto.MapResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/maps")
class MapController @Autowired constructor(val mapService: MapService) {
    @GetMapping
    fun findMap(): ResponseEntity<MapResponse> {
        return ResponseEntity.ok(mapService.findMap())
    }
}
