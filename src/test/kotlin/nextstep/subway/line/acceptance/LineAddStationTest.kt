package nextstep.subway.line.acceptance

import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_응답됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선_마지막에_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선_중간에_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록_실패됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록됨
import nextstep.subway.line.acceptance.LineAddStationStep.노선정보에_역정보_포함됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import nextstep.subway.station.dto.StationResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철 노선에 역 등록 기능")
class LineAddStationTest : AcceptanceTest() {
    var station1: StationResponse = StationResponse(1, "test", null, null)
    var station2: StationResponse = StationResponse(2, "test", null, null)
    var station3: StationResponse = StationResponse(3, "test", null, null)
    var lineId: Long = 1

    @BeforeEach
    fun backGround() {
        station1 = 등록한_지하철역정보_요청("피카츄역")
        station2 = 등록한_지하철역정보_요청("라이츄역")
        station3 = 등록한_지하철역정보_요청("주한역")
        lineId = 등록한_노선정보_요청(name = "주한선", color = "bg-red-600", intervalTime = "5").id
    }

    @DisplayName("지하철 노선에 역을 등록한다.")
    @Test
    fun addStationOfLine() {
        // when
        val response = 노선에_역_등록_요청(station1.id, lineId)

        // then
        노선에_역_등록됨(response)
    }

    @DisplayName("지하철 노선 상세정보 조회 시 역 정보가 포함된다.")
    @Test
    fun containsStation() {
        // Given
        노선에_역_등록되어_있음(station1.id, lineId)

        // When
        val response = 노선_조회_요청(lineId)

        // then
        노선_조회_응답됨(response)
        노선정보에_역정보_포함됨(response)
    }

    @DisplayName("지하철 노선에 역을 마지막에 등록한다.")
    @Test
    fun addStationOfLine2() {
        // Given
        노선에_역_등록되어_있음(station1.id, lineId)

        // when
        val lineStationResponse = 노선에_역_등록_요청(station2.id, lineId)

        // then
        노선에_역_등록됨(lineStationResponse)

        // When
        val response = 노선_조회_요청(lineId)

        // then
        노선_조회_응답됨(response)
        노선_마지막에_등록됨(response, "라이츄역")
    }

    @DisplayName("지하철 노선에 역을 중간에 등록한다.")
    @Test
    fun addStationOfLine3() {
        // Given
        노선에_역_등록되어_있음(station1.id, lineId)
        노선에_역_등록되어_있음(station2.id, lineId)

        // when
        val response1 = 노선에_역_등록_요청(station3.id, lineId, station1.id)

        // then
        노선에_역_등록됨(response1)

        // When
        val response2 = 노선_조회_요청(lineId)

        // then
        노선_조회_응답됨(response2)
        노선_중간에_등록됨(response2, mapOf("first" to station1.name, "second" to station3.name, "last" to station2.name))
    }

    @DisplayName("이미 등록되어 있던 역을 등록한다.")
    @Test
    fun addStationOfLine4() {
        // Given
        노선에_역_등록되어_있음(station1.id, lineId)

        // When
        val response = 노선에_역_등록_요청(station1.id, lineId)

        // Then
        노선에_역_등록_실패됨(response)
    }

    @DisplayName("존재하지 않는 역을 등록한다.")
    @Test
    fun addStationOfLine5() {
        // When
        val response = 노선에_역_등록_요청(1000, lineId)

        // Then
        노선에_역_등록_실패됨(response)
    }
}
