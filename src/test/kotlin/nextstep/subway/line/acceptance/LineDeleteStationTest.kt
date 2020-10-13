package nextstep.subway.line.acceptance

import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.line.acceptance.LineDeleteStationStep.노선에_등록된_역_제외_요청
import nextstep.subway.line.acceptance.LineDeleteStationStep.노선에_등록된_역_제외됨
import nextstep.subway.line.acceptance.LineDeleteStationStep.노선에_역_순서_정렬됨
import nextstep.subway.line.acceptance.LineDeleteStationStep.노선에_역_제외_실패됨
import nextstep.subway.line.acceptance.LineDeleteStationStep.노선에_역_제외_확인됨
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철 노선에서 역 제외 기능")
class LineDeleteStationTest: AcceptanceTest() {
    var station1Id: Long = 0
    var station2Id: Long = 0
    var station3Id: Long = 0
    var lineId: Long = 0

    @BeforeEach
    fun backGround() {
        station1Id = 등록한_지하철역정보_요청("주한역").id
        station2Id = 등록한_지하철역정보_요청("카츄역").id
        station3Id = 등록한_지하철역정보_요청("박츄역").id
        lineId = 등록한_노선정보_요청(name = "주한선", color = "bg-red-600", intervalTime = "5").id
        노선에_역_등록되어_있음(station1Id, lineId)
        노선에_역_등록되어_있음(station3Id, lineId)
        노선에_역_등록되어_있음(station2Id, lineId)
    }

    @DisplayName("지하철 노선에 등록된 마지막 역을 제외한다.")
    @Test
    fun deleteStationOfLine() {
        // When
        val response = 노선에_등록된_역_제외_요청(lineId, station2Id)

        // Given
        노선에_등록된_역_제외됨(response)

        // When
        val lineResponse = 노선_조회_요청(lineId).`as`(LineResponse::class.java)

        // Given
        노선에_역_제외_확인됨(lineResponse, station2Id)
        노선에_역_순서_정렬됨(lineResponse, station1Id, station3Id)
    }

    @DisplayName("지하철 노선에 등록된 중간 역을 제외한다.")
    @Test
    fun deleteStationOfLine2() {
        // When
        val response = 노선에_등록된_역_제외_요청(lineId, station3Id)

        // Given
        노선에_등록된_역_제외됨(response)

        // When
        val lineResponse = 노선_조회_요청(lineId).`as`(LineResponse::class.java)

        // Given
        노선에_역_제외_확인됨(lineResponse, station3Id)
        노선에_역_순서_정렬됨(lineResponse, station1Id, station2Id)
    }

    @DisplayName("지하철 노선에 등록되지 않은 역을 제외한다.")
    @Test
    fun deleteStationOfLine3() {
        // When
        val response = 노선에_등록된_역_제외_요청(lineId, 1000)

        // Given
        노선에_역_제외_실패됨(response)
    }
}
