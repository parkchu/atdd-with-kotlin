package nextstep.subway.station.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_등록되어_있음
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_목록_응답됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_목록_조회_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_목록_포함됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_삭제됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_실패됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성_요청
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_생성됨
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_제거_요청
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("지하철역 관련 기능")
class StationAcceptanceTest : AcceptanceTest() {
    @DisplayName("지하철역을 생성한다.")
    @Test
    fun createStation() {
        // when
        val response: ExtractableResponse<Response> = 지하철역_생성_요청("강남역")

        // then
        지하철역_생성됨(response)
    }

    @DisplayName("기존에 존재하는 지하철역 이름으로 지하철역을 생성한다.")
    @Test
    fun createStation2() {
        //given
        지하철역_등록되어_있음("강남역")

        // when
        val response: ExtractableResponse<Response> = 지하철역_생성_요청("강남역")

        // then
        지하철역_생성_실패됨(response)
    }

    // given
    @Test
    @DisplayName("지하철역을 조회한다.")
    fun getStations() {
        // given
        val createResponse1: ExtractableResponse<Response> = 지하철역_등록되어_있음("강남역")
        val createResponse2: ExtractableResponse<Response> = 지하철역_등록되어_있음("역삼역")

        // when
        val response: ExtractableResponse<Response> = 지하철역_목록_조회_요청()

        // then
        지하철역_목록_응답됨(response)
        지하철역_목록_포함됨(response, listOf(createResponse1, createResponse2))
    }

    @DisplayName("지하철역을 제거한다.")
    @Test
    fun deleteStation() {
        // given
        val createResponse: ExtractableResponse<Response> = 지하철역_등록되어_있음("강남역")

        // when
        val response: ExtractableResponse<Response> = 지하철역_제거_요청(createResponse)

        // then
        지하철역_삭제됨(response)
    }
}
