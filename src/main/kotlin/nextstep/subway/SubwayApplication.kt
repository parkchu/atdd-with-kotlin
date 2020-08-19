package nextstep.subway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SubwayApplication

fun main(args: Array<String>) {
    runApplication<SubwayApplication>(*args)
}
