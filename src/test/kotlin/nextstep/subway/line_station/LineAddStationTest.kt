package nextstep.subway.line_station

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성됨
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("지하철 노선에 역 등록 기능")
class LineAddStationTest : AcceptanceTest() {
    @DisplayName("백그라운드가 요청되었는지 테스트")
    @Test
    fun testBackground() {
        // Background
        val stationResponse = 지하철역_생성_요청("피카츄역")
        val lineResponse = 노선_생성_요청("주한선", "bg-red-600", "5")

        //then
        지하철역_생성됨(stationResponse)
        노선_생성됨(lineResponse)
    }

    @DisplayName("지하철 노선에 역을 등록한다.")
    @Test
    fun addStationOfLine() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val lineResponse = 등록한_노선정보_요청("주한선", "bg-red-600", "5")

        // when
        val params: MutableMap<String, Long?> = HashMap()
        params["lineId"] = lineResponse.id
        params["preStationId"] = null
        params["stationId"] = stationResponse.id
        params["distance"] = 10
        params["time"] = 10
        val uri = "/lines/${lineResponse.id}"
        val response = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()

        // then
        val afterLine = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()[uri].then().log().all().extract().`as`(LineResponse::class.java)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(afterLine.stations).isNotEmpty
    }

    @DisplayName("지하철 노선 상세정보 조회 시 역 정보가 포함된다.")
    @Test
    fun containsStation() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val lineResponse = 등록한_노선정보_요청("주한선", "bg-red-600", "5")

        // Given
        val params: MutableMap<String, Long?> = HashMap()
        params["lineId"] = lineResponse.id
        params["preStationId"] = null
        params["stationId"] = stationResponse.id
        params["distance"] = 10
        params["time"] = 10
        val uri = "/lines/${lineResponse.id}"
        RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()

        // When
        val response = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()[uri].then().log().all().extract()

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.`as`(LineResponse::class.java).stations).isNotEmpty
    }
}
