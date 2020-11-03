package nextstep.subway.path.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("지하철 경로 검색")
class FindShortestPathTest : AcceptanceTest() {

    /* @DisplayName("두 역의 최단 거리 경로를 조회")
    @Test
    fun findShortestPath() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val station4 = 등록한_지하철역정보_요청("영정역")
        val station5 = 등록한_지하철역정보_요청("예은역")
        val line = 등록한_노선정보_요청("1호선", "bg-red-600", "5")
        노선에_역_등록되어_있음(station1.id, line.id)
        노선에_역_등록되어_있음(station2.id, line.id)
        노선에_역_등록되어_있음(station3.id, line.id)
        노선에_역_등록되어_있음(station4.id, line.id)
        노선에_역_등록되어_있음(station5.id, line.id)

        // When
        val response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths?source=${station1.id}&target=${station5.id}"]
                .then().log().all().extract()

        // Then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.`as`(PathResponse::class.java).distance).isEqualTo(50)
        assertThat(response.`as`(PathResponse::class.java).duration).isEqualTo(50)
        assertThat(response.`as`(PathResponse::class.java).stations).hasSize(5)
    } */
}
