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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
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

    @DisplayName("지하철 노선에 역을 마지막에 등록한다.")
    @Test
    fun addStationOfLine2() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val stationResponse2 = 등록한_지하철역정보_요청("라이츄역")
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

        // when
        params["stationId"] = stationResponse2.id
        val response1 = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()

        // then
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.OK.value())

        // When
        val response2 = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()[uri].then().log().all().extract()
        val response2Body = response2.`as`(LineResponse::class.java)

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response2Body.stations).isNotEmpty
        assertThat(response2Body.stations.last().station.name).isEqualTo("라이츄역")
    }

    @DisplayName("지하철 노선에 역을 중간에 등록한다.")
    @Test
    fun addStationOfLine3() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val stationResponse2 = 등록한_지하철역정보_요청("라이츄역")
        val stationResponse3 = 등록한_지하철역정보_요청("주한역")
        val lineResponse = 등록한_노선정보_요청("주한선", "bg-red-600", "5")

        // Given
        val params: MutableMap<String, Long?> = HashMap()
        params["lineId"] = lineResponse.id
        params["preStationId"] = null
        params["stationId"] = stationResponse.id
        params["distance"] = 10
        params["duration"] = 10
        val uri = "/lines/${lineResponse.id}"
        RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()
        params["stationId"] = stationResponse2.id
        RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()

        // when
        params["preStationId"] = stationResponse.id
        params["stationId"] = stationResponse3.id
        val response1 = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("$uri/stations")
                .then()
                .log().all().extract()

        // then
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.OK.value())

        // When
        val response2 = RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).`when`()[uri].then().log().all().extract()
        val response2Body = response2.`as`(LineResponse::class.java)

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response2Body.stations).isNotEmpty
        assertThat(response2Body.stations.first().station.name).isNotEqualTo("주한역")
        assertThat(response2Body.stations.last().station.name).isNotEqualTo("주한역")
        assertThat(response2Body.stations[1].station.name).isEqualTo("주한역")
    }
}
