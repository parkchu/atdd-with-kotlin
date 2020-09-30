package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_등록되어_있음
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_목록_조회_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_실패됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_수정_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_제거_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.dto.LineResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("지하철 노선 관련 기능")
class LineAcceptanceTest : AcceptanceTest() {
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    fun createLine() {
        // when
        // 지하철_노선_생성_요청
        val response = 노선_생성_요청(name = "신분당선", color = "bg-red-600", intervalTime = "5")

        // then
        // 지하철_노선_생성됨
        노선_생성됨(response)
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    fun createLine2() {
        // given
        // 지하철_노선_등록되어_있음
        노선_등록되어_있음("신분당선", "bg-red-600", "5")

        // when
        // 지하철_노선_생성_요청
        val response = 노선_생성_요청(name = "신분당선", color = "bg-red-600", intervalTime = "5")

        // then
        // 지하철_노선_생성_실패됨
        노선_생성_실패됨(response)
    }

    @Test
    @DisplayName("지하철 노선 목록을 조회한다.")
    fun getLines() {
        // given
        // 지하철_노선_등록되어_있음
        // 지하철_노선_등록되어_있음
        val lineResponse1 = 등록한_노선정보_요청("신분당선", "bg-red-600", "5")
        val lineResponse2 = 등록한_노선정보_요청("피카츄선", "bg-yellow-600", "10")

        // when
        // 지하철_노선_목록_조회_요청
        val response = 노선_목록_조회_요청()

        // then
        // 지하철_노선_목록_응답됨
        // 지하철_노선_목록_포함됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.body().`as`(Array<LineResponse>::class.java).toList().containsAll(listOf(lineResponse1, lineResponse2))).isTrue()
    }

    @Test
    @DisplayName("지하철 노선을 조회한다.")
    fun getLine() {
        // given
        // 지하철_노선_등록되어_있음
        val createResponse = 노선_등록되어_있음("신분당선", "bg-red-600", "5")

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
        val lineResponse = 등록한_노선정보_요청("피카츄선", "bg-yellow-600", "5")

        // when
        // 지하철_노선_수정_요청
        val updateResponse = 노선_수정_요청(lineResponse, name = "라이츄선", color = "bg-orange-600")

        // then
        // 지하철_노선_수정됨
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        val response = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()["/lines/${lineResponse.id}"].then().log().all().extract().`as`(LineResponse::class.java)
        assertThat(response.name).isEqualTo("라이츄선")
        assertThat(response.color).isEqualTo("bg-orange-600")
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    fun deleteLine() {
        // given
        // 지하철_노선_등록되어_있음
        val lineResponse = 노선_등록되어_있음("피카츄선", "bg-red-600", "5")

        // when
        // 지하철_노선_제거_요청
        val response = 노선_제거_요청(lineResponse)

        // then
        // 지하철_노선_삭제됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        val lineListResponse = 노선_목록_조회_요청()
        assertThat(lineListResponse.`as`(Array<LineResponse>::class.java)).isEmpty()
    }
}
