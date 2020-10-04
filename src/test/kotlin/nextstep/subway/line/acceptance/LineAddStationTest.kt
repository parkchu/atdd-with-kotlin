package nextstep.subway.line.acceptance

import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_응답됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선_마지막에_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선_중간에_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선정보에_역정보_포함됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성됨
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철 노선에 역 등록 기능")
class LineAddStationTest : AcceptanceTest() {
    @DisplayName("백그라운드가 요청되었는지 테스트")
    @Test
    fun testBackground() {
        // Background
        val stationResponse = 지하철역_생성_요청("피카츄역")
        val lineResponse = 노선_생성_요청(name = "주한선", color = "bg-red-600", intervalTime = "5")

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
        val response = 노선에_역_등록_요청(stationResponse, lineResponse)

        // then
        노선에_역_등록됨(response)
    }

    @DisplayName("지하철 노선 상세정보 조회 시 역 정보가 포함된다.")
    @Test
    fun containsStation() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val lineResponse = 등록한_노선정보_요청("주한선", "bg-red-600", "5")

        // Given
        노선에_역_등록되어_있음(stationResponse, lineResponse)

        // When
        val response = 노선_조회_요청("/lines/${lineResponse.id}")

        // then
        노선_조회_응답됨(response)
        노선정보에_역정보_포함됨(response)
    }

    @DisplayName("지하철 노선에 역을 마지막에 등록한다.")
    @Test
    fun addStationOfLine2() {
        // Background
        val stationResponse = 등록한_지하철역정보_요청("피카츄역")
        val stationResponse2 = 등록한_지하철역정보_요청("라이츄역")
        val lineResponse = 등록한_노선정보_요청("주한선", "bg-red-600", "5")

        // Given
        노선에_역_등록되어_있음(stationResponse, lineResponse)

        // when
        val lineStationResponse = 노선에_역_등록_요청(stationResponse2, lineResponse)

        // then
        노선에_역_등록됨(lineStationResponse)

        // When
        val response = 노선_조회_요청("/lines/${lineResponse.id}")

        // then
        노선_조회_응답됨(response)
        노선_마지막에_등록됨(response, stationResponse2.name)
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
        노선에_역_등록되어_있음(stationResponse, lineResponse)
        노선에_역_등록되어_있음(stationResponse2, lineResponse)

        // when
        val response1 = 노선에_역_등록_요청(stationResponse3, lineResponse, stationResponse.id)

        // then
        노선에_역_등록됨(response1)

        // When
        val response2 = 노선_조회_요청("/lines/${lineResponse.id}")

        // then
        노선_조회_응답됨(response2)
        노선_중간에_등록됨(response2, mapOf("first" to stationResponse.name, "second" to stationResponse3.name, "last" to stationResponse2.name))
    }
}
