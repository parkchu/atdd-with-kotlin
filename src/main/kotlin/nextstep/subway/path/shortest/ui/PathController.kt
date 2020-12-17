package nextstep.subway.path.shortest.ui

import nextstep.subway.member.domain.LoginMember
import nextstep.subway.path.shortest.application.PathService
import nextstep.subway.path.shortest.dto.PathResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/paths")
class PathController @Autowired constructor(val pathService: PathService) {
    @GetMapping("/")
    fun findShortestPath(@RequestParam("source") startStationId: Long, @RequestParam("target") arrivalStationId: Long, @RequestParam("type") type: String, @RequestParam("time", defaultValue = "") time: String, loginMember: LoginMember): ResponseEntity<PathResponse> {
        val stations = listOf(startStationId, arrivalStationId)
        val response = if (type == "ARRIVAL_TIME") {
            pathService.findFastestPath(stations, time)
        } else {
            pathService.findShortest(stations, type)
        }
        response.updateFareWithAge(loginMember.age)
        return ResponseEntity.ok().body(response)
    }
}
