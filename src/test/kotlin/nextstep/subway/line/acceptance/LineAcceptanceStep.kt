package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.line.dto.LineResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LineAcceptanceStep {
    fun 지하철_노선_등록되어_있음(name: String, color: String, intervalTime: String): ExtractableResponse<Response> {
        return 노선_생성_요청(name = name, color = color, intervalTime = intervalTime)
    }

    fun 노선_생성_요청(
            name: String,
            color: String,
            startTime: LocalTime = LocalTime.of(5, 30),
            endTime: LocalTime = LocalTime.of(23, 30),
            intervalTime: String,
            uri: String? = null
    ): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["name"] = name
        params["color"] = color
        params["startTime"] = startTime.format(DateTimeFormatter.ISO_TIME)
        params["endTime"] = endTime.format(DateTimeFormatter.ISO_TIME)
        params["intervalTime"] = intervalTime
        val requestSpecification = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
        return if (uri.isNullOrBlank()) {
            requestSpecification.post("/lines")
        } else {
            requestSpecification.put(uri)
        }
                .then()
                .log().all().extract()
    }

    fun 등록한_노선정보_요청(name: String, color: String, intervalTime: String): LineResponse {
        return 노선_생성_요청(name = name, color = color, intervalTime = intervalTime).body().`as`(LineResponse::class.java)
    }

    fun 노선_수정_요청(
            line: LineResponse,
            name: String = line.name,
            color: String = line.color,
            startTime: LocalTime = line.startTime,
            endTime: LocalTime = line.endTime,
            intervalTime: String = line.intervalTime.toString()
    ): ExtractableResponse<Response> {
        return 노선_생성_요청(name, color, startTime, endTime, intervalTime, "/lines/${line.id}")
    }

    fun 노선_목록_조회_요청(): ExtractableResponse<Response> {
        return RestAssured
                .given().log().all()
                .`when`()["/lines"]
                .then().log().all().extract()
    }

    fun 노선_조회_요청(lineId: Long): ExtractableResponse<Response> {
        return RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()["/lines/$lineId"].then().log().all().extract()
    }

    fun 노선_제거_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured
                .given().log().all().`when`().delete(uri)
                .then().log().all().extract()
    }

    fun 노선_생성됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.header("Location")).isNotBlank()
    }

    fun 노선_생성_실패됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.header("Location")).isBlank()
    }

    fun 노선_응답됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 노선_목록_포함됨(response: ExtractableResponse<Response>, list: List<LineResponse>) {
        assertThat(response.body().`as`(Array<LineResponse>::class.java).toList().containsAll(list)).isTrue()
    }

    fun 노선_조회_응답됨(response: ExtractableResponse<Response>) {
        노선_응답됨(response)
        assertThat(response.`as`(LineResponse::class.java)).isNotNull()
    }

    fun 노선_수정됨(response: ExtractableResponse<Response>, lineId: Long) {
        노선_응답됨(response)
        val lineResponse = 노선_조회_요청(lineId).`as`(LineResponse::class.java)
        assertThat(lineResponse.name).isEqualTo("라이츄선")
        assertThat(lineResponse.color).isEqualTo("bg-orange-600")
    }

    fun 노선_삭제됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        val lineListResponse = 노선_목록_조회_요청()
        assertThat(lineListResponse.`as`(Array<LineResponse>::class.java)).isEmpty()
    }
}
