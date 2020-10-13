package nextstep.subway.station.ui

import mu.KotlinLogging
import nextstep.subway.line.application.LineService
import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationCreateRequest
import nextstep.subway.station.dto.StationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

val logger = KotlinLogging.logger { }

@RestController
class StationController @Autowired constructor(
        val lineService: LineService,
        private val stationRepository: StationRepository
) {
    @PostMapping("/stations")
    fun createStation(@RequestBody request: StationCreateRequest): ResponseEntity<*> {
        logger.debug { "StationCreateRequest : $request" }
        return try {
            val persistStation = stationRepository.save(request.toStation())
            ResponseEntity.created(URI.create("/stations/" + persistStation.id)).body(StationResponse.of(persistStation))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build<Void>()
        }
    }

    @GetMapping(value = ["/stations"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun showStations(): ResponseEntity<*> {
        return ResponseEntity.ok().body(stationRepository.findAll())
    }

    @DeleteMapping("/stations/{id}")
    fun deleteStation(@PathVariable id: Long): ResponseEntity<Void> {
        stationRepository.deleteById(id)
        lineService.deleteStationAllLine(id)
        return ResponseEntity.noContent().build()
    }
}
