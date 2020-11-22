package nextstep.subway.favorite.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_목록_조회_요청
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_목록_조회됨
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_삭제_요청
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_삭제됨
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_생성됨
import nextstep.subway.favorite.acceptance.step.FavoriteAcceptanceStep.즐겨찾기_생성을_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.지하철_노선_등록되어_있음
import nextstep.subway.line.acceptance.LineStationAcceptanceStep.지하철_노선에_지하철역_등록되어_있음
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.로그인_되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_등록되어_있음
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_등록되어_있음
import nextstep.subway.station.dto.StationResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("즐겨찾기 관련 기능")
class FavoriteAcceptanceTest : AcceptanceTest() {
    /**
     * 교대역      -      강남역
     * |                 |
     * 남부터미널역           |
     * |                 |
     * 양재역      -       -|
     */
    @BeforeEach
    fun backGround() {
        val createdStationResponse1: ExtractableResponse<Response> = 지하철역_등록되어_있음("교대역")
        val createdStationResponse2: ExtractableResponse<Response> = 지하철역_등록되어_있음("강남역")
        val createdStationResponse3: ExtractableResponse<Response> = 지하철역_등록되어_있음("양재역")
        val createdStationResponse4: ExtractableResponse<Response> = 지하철역_등록되어_있음("남부터미널")
        val createLineResponse1: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("2호선", "GREEN")
        val createLineResponse2: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("신분당선", "RED")
        val createLineResponse3: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("3호선", "ORANGE")
        val lineId1 = createLineResponse1.`as`(LineResponse::class.java).id
        val lineId2 = createLineResponse2.`as`(LineResponse::class.java).id
        val lineId3 = createLineResponse3.`as`(LineResponse::class.java).id
        val stationId1 = createdStationResponse1.`as`(StationResponse::class.java).id
        val stationId2 = createdStationResponse2.`as`(StationResponse::class.java).id
        val stationId3 = createdStationResponse3.`as`(StationResponse::class.java).id
        val stationId4 = createdStationResponse4.`as`(StationResponse::class.java).id
        지하철_노선에_지하철역_등록되어_있음(lineId1, null, stationId1, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId1, stationId1, stationId2, 2, 2)
        지하철_노선에_지하철역_등록되어_있음(lineId2, null, stationId2, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId1, stationId2, stationId3, 2, 1)
        지하철_노선에_지하철역_등록되어_있음(lineId3, null, stationId1, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId3, stationId1, stationId4, 1, 2)
        지하철_노선에_지하철역_등록되어_있음(lineId3, stationId4, stationId3, 2, 2)
        회원_등록되어_있음(EMAIL, PASSWORD, 20)
    }

    @DisplayName("회원 별 즐겨찾기 관리")
    @Test
    fun manageMember() {
        // backGround
        val token = 로그인_되어_있음(EMAIL, PASSWORD)

        // When
        val response = 즐겨찾기_생성을_요청(1, 2, token)

        // Then
        즐겨찾기_생성됨(response)

        // When
        val response2 = 즐겨찾기_목록_조회_요청(token)

        // Then
        즐겨찾기_목록_조회됨(response2)

        // When
        val response3 = 즐겨찾기_삭제_요청(response, token)

        // Then
        즐겨찾기_삭제됨(response3)

    }


    companion object {
        const val EMAIL = "email@email.com"
        const val PASSWORD = "password"
    }
}
