package nextstep.subway.map.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.map.dto.MapResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("지하철 노선도 조회 기능")
class MapLookupTest : AcceptanceTest() {

    @DisplayName("지하철 노선도를 조회한다.")
    @Test
    fun lookupRouteMap() {
        // Given
        val station1 = 등록한_지하철역정보_요청("일역")
        val station2 = 등록한_지하철역정보_요청("이역")
        val station3 = 등록한_지하철역정보_요청("삼역")
        val station4 = 등록한_지하철역정보_요청("사역")
        val station5 = 등록한_지하철역정보_요청("오역")
        val line1 = 등록한_노선정보_요청("1호선", "bg-red-600", "5")
        val line2 = 등록한_노선정보_요청("2호선", "bg-red-600", "5")
        노선에_역_등록되어_있음(station1.id, line1.id)
        노선에_역_등록되어_있음(station2.id, line1.id)
        노선에_역_등록되어_있음(station3.id, line1.id)
        노선에_역_등록되어_있음(station1.id, line2.id)
        노선에_역_등록되어_있음(station4.id, line2.id)
        노선에_역_등록되어_있음(station5.id, line2.id)

        // When
        val response = RestAssured
                .given()
                .log().all()
                .header("If-None-Match", "")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()
                .get("/maps")
                .then()
                .header("ETag", notNullValue())
                .log().all().extract()

        // Then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        val mapResponse = response.`as`(MapResponse::class.java)
        assertThat(mapResponse.lineResponses[0].stations.first().station.id).isEqualTo(station1.id)
        assertThat(mapResponse.lineResponses[0].stations[1].station.id).isEqualTo(station2.id)
        assertThat(mapResponse.lineResponses[0].stations.last().station.id).isEqualTo(station3.id)
        assertThat(mapResponse.lineResponses[1].stations.first().station.id).isEqualTo(station1.id)
        assertThat(mapResponse.lineResponses[1].stations[1].station.id).isEqualTo(station4.id)
        assertThat(mapResponse.lineResponses[1].stations.last().station.id).isEqualTo(station5.id)
    }
}
