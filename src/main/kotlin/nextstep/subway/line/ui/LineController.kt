package nextstep.subway.line.ui

import mu.KotlinLogging
import nextstep.subway.line.application.LineService
import nextstep.subway.line.domain.Line
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/lines")
class LineController @Autowired constructor(val lineService: LineService) {
    @PostMapping
    fun createLine(@RequestBody lineRequest: LineRequest): ResponseEntity<LineResponse> {
        logger.debug { "LineRequest : $lineRequest" }
        val line: Line = lineService.saveLine(lineRequest)
        return ResponseEntity.created(URI.create("/lines/" + line.id)).body(LineResponse.of(line))
    }

    @GetMapping
    fun findAllLines(): ResponseEntity<List<LineResponse>> {
        return ResponseEntity.ok(lineService.findAllLines())
    }

    @GetMapping("/{id}")
    fun findLineById(@PathVariable id: Long): ResponseEntity<LineResponse> {
        return ResponseEntity.ok(lineService.findLineById(id))
    }

    @PutMapping("/{id}")
    fun updateLine(@PathVariable id: Long, @RequestBody lineUpdateRequest: LineRequest): ResponseEntity<Void> {
        lineService.updateLine(id, lineUpdateRequest)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteLine(@PathVariable id: Long): ResponseEntity<*> {
        lineService.deleteLineById(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleIllegalArgsException(e: DataIntegrityViolationException?): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<Any>()
    }
}
