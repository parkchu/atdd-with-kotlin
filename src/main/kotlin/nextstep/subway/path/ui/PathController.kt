package nextstep.subway.path.ui

import nextstep.subway.member.domain.LoginMember
import nextstep.subway.path.application.PathService
import nextstep.subway.path.dto.PathResponse
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
    fun findShortestPath(@RequestParam("source") startStationId: Long, @RequestParam("target") arrivalStationId: Long, @RequestParam("type") type: String, loginMember: LoginMember): ResponseEntity<PathResponse> {
        val response = pathService.findShortest(listOf(startStationId, arrivalStationId), type)
        pathService.updatePriceToResponse(response, loginMember.age)
        return ResponseEntity.ok().body(response)
    }
}
