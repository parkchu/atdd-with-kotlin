package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.dto.LineResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@DisplayName("지하철 노선 관련 기능")
class LineAcceptanceTest : AcceptanceTest() {
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    fun createLine() {
        // when
        // 지하철_노선_생성_요청
        val params: MutableMap<String, String> = HashMap()
        params["name"] = "신분당선"
        params["color"] = "bg-red-600"
        params["startTime"] = LocalTime.of(5, 30).format(DateTimeFormatter.ISO_TIME)
        params["endTime"] = LocalTime.of(23, 30).format(DateTimeFormatter.ISO_TIME)
        params["intervalTime"] = "5"
        val response = RestAssured
                .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                    .post("/lines")
                .then()
                    .log().all().extract()

        // then
        // 지하철_노선_생성됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.header("Location")).isNotBlank()
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    fun createLine2() {
        // given
        // 지하철_노선_등록되어_있음

        // when
        // 지하철_노선_생성_요청

        // then
        // 지하철_노선_생성_실패됨
    }

    // given
    // 지하철_노선_등록되어_있음
    // 지하철_노선_등록되어_있음

    // when
    // 지하철_노선_목록_조회_요청

    // then
    // 지하철_노선_목록_응답됨
    // 지하철_노선_목록_포함됨
    @get:Test
    @get:DisplayName("지하철 노선 목록을 조회한다.")
    val lines: Unit
        get() {
            // given
            // 지하철_노선_등록되어_있음
            // 지하철_노선_등록되어_있음

            // when
            // 지하철_노선_목록_조회_요청

            // then
            // 지하철_노선_목록_응답됨
            // 지하철_노선_목록_포함됨
        }

    // given
    // 지하철_노선_등록되어_있음
    @get:Test
    @get:DisplayName("지하철 노선을 조회한다.")
    val line:

            // when
            // 지하철_노선_조회_요청

            // then
            // 지하철_노선_응답됨
            Unit
        get() {
            // given
            // 지하철_노선_등록되어_있음
            val params: MutableMap<String, String> = HashMap()
            params["name"] = "신분당선"
            params["color"] = "bg-red-600"
            params["startTime"] = LocalTime.of(5, 30).format(DateTimeFormatter.ISO_TIME)
            params["endTime"] = LocalTime.of(23, 30).format(DateTimeFormatter.ISO_TIME)
            params["intervalTime"] = "5"
            val createResponse = RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(params).`when`().post("/lines").then().log().all().extract()

            // when
            // 지하철_노선_조회_요청
            val uri = createResponse.header("Location")
            val response = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()[uri].then().log().all().extract()

            // then
            // 지하철_노선_응답됨
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            assertThat(response.`as`(LineResponse::class.java)).isNotNull()
        }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    fun updateLine() {
        // given
        // 지하철_노선_등록되어_있음

        // when
        // 지하철_노선_수정_요청

        // then
        // 지하철_노선_수정됨
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    fun deleteLine() {
        // given
        // 지하철_노선_등록되어_있음

        // when
        // 지하철_노선_제거_요청

        // then
        // 지하철_노선_삭제됨
    }
}
