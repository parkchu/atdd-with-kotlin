package nextstep.subway.map.acceptance

import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.map.acceptance.MapLookupStep.노선도_조회_실패됨
import nextstep.subway.map.acceptance.MapLookupStep.노선도_조회_요청
import nextstep.subway.map.acceptance.MapLookupStep.노선도_조회됨
import nextstep.subway.map.acceptance.MapLookupStep.노선별_지하철역_순서_정렬됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import nextstep.subway.station.dto.StationResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철 노선도 조회 기능")
class MapLookupTest : AcceptanceTest() {
    val stations = mutableListOf<StationResponse>()
    val lines = mutableListOf<LineResponse>()

    @BeforeEach
    fun backGround() {
        stations.clear()
        repeat(5) { stations.add(등록한_지하철역정보_요청("$it")) }
        lines.clear()
        repeat(2) { lines.add(등록한_노선정보_요청("${it}호선", "bg-red-600", "5")) }
        노선에_역_등록되어_있음(stations[0].id, lines[0].id)
        노선에_역_등록되어_있음(stations[1].id, lines[0].id)
        노선에_역_등록되어_있음(stations[2].id, lines[0].id)
        노선에_역_등록되어_있음(stations[0].id, lines[1].id)
        노선에_역_등록되어_있음(stations[3].id, lines[1].id)
        노선에_역_등록되어_있음(stations[4].id, lines[1].id)
    }

    @DisplayName("지하철 노선도를 조회한다.")
    @Test
    fun lookupRouteMap() {
        // When
        val response = 노선도_조회_요청()

        // Then
        노선도_조회됨(response)
        노선별_지하철역_순서_정렬됨(response, stations)
    }

    @DisplayName("변하지 않은 지하철 노선도를 조회한다.")
    @Test
    fun lookupRouteMap2() {
        val response = 노선도_조회_요청()

        // When
        val eTag = response.header("ETag")
        val response2 = 노선도_조회_요청(eTag)

        // Then
        노선도_조회_실패됨(response2)
    }
}
