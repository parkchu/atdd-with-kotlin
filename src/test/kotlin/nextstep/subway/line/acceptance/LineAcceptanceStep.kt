package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.line.dto.LineResponse
import org.springframework.http.MediaType
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LineAcceptanceStep {
    fun 노선_등록되어_있음(name: String, color: String, intervalTime: String): ExtractableResponse<Response> {
        return 노선_생성_요청(name, color, intervalTime)
    }

    fun 노선_생성_요청(name: String, color: String, intervalTime: String): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["name"] = name
        params["color"] = color
        params["startTime"] = LocalTime.of(5, 30).format(DateTimeFormatter.ISO_TIME)
        params["endTime"] = LocalTime.of(23, 30).format(DateTimeFormatter.ISO_TIME)
        params["intervalTime"] = intervalTime
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("/lines")
                .then()
                .log().all().extract()
    }

    fun 등록한_노선정보_요청(name: String, color: String, intervalTime: String): LineResponse {
        return 노선_생성_요청(name, color, intervalTime).body().`as`(LineResponse::class.java)
    }

    fun 노선_수정_요청(
            line: LineResponse,
            name: String = line.name,
            color: String = line.color,
            startTime: LocalTime = line.startTime,
            endTime: LocalTime = line.endTime,
            intervalTime: String = line.intervalTime.toString()
    ): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["name"] = name
        params["color"] = color
        params["startTime"] = startTime.format(DateTimeFormatter.ISO_TIME)
        params["endTime"] = endTime.format(DateTimeFormatter.ISO_TIME)
        params["intervalTime"] = intervalTime
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .put("/lines/${line.id}")
                .then()
                .log().all().extract()
    }
}
