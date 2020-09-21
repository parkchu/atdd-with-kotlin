package nextstep.subway.line_station

import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_실패됨
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_생성_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_실패됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_요청
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철 노선에 역 등록 기능")
class LineAddStationTest : AcceptanceTest() {
    @DisplayName("백그라운드가 요청되었는지 테스트")
    @Test
    fun testBackground() {
        // Background
        지하철역_생성_요청("피카츄역")
        노선_생성_요청("주한선", "bg-red-600", "5")

        // when
        val stationResponse = 지하철역_생성_요청("피카츄역")
        val lineResponse = 노선_생성_요청("주한선", "bg-red-600", "5")

        //then
        지하철역_생성_실패됨(stationResponse)
        노선_생성_실패됨(lineResponse)
    }
}
