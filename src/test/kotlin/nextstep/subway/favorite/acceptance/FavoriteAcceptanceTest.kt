package nextstep.subway.favorite.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.지하철_노선_등록되어_있음
import nextstep.subway.line.acceptance.step.LineAcceptanceStep.지하철_노선_등록되어_있음
import nextstep.subway.line.acceptance.step.LineStationAcceptanceStep.지하철_노선에_지하철역_등록되어_있음
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_등록되어_있음
import nextstep.subway.station.acceptance.StationAcceptanceStep.지하철역_등록되어_있음
import nextstep.subway.station.acceptance.step.StationAcceptanceStep.지하철역_등록되어_있음
import nextstep.subway.station.dto.StationResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("즐겨찾기 관련 기능")
class FavoriteAcceptanceTest : AcceptanceTest() {
    private var lineId1: Long = null
    private var lineId2: Long = null
    private var lineId3: Long? = null
    private var stationId1: Long? = null
    private var stationId2: Long? = null
    private var stationId3: Long? = null
    private var stationId4: Long? = null

    /**
     * 교대역      -      강남역
     * |                 |
     * 남부터미널역           |
     * |                 |
     * 양재역      -       -|
     */
    @BeforeEach
    override fun setUp() {
        super.setUp()
        val createdStationResponse1: ExtractableResponse<Response> = 지하철역_등록되어_있음("교대역")
        val createdStationResponse2: ExtractableResponse<Response> = 지하철역_등록되어_있음("강남역")
        val createdStationResponse3: ExtractableResponse<Response> = 지하철역_등록되어_있음("양재역")
        val createdStationResponse4: ExtractableResponse<Response> = 지하철역_등록되어_있음("남부터미널")
        val createLineResponse1: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("2호선", "GREEN")
        val createLineResponse2: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("신분당선", "RED")
        val createLineResponse3: ExtractableResponse<Response> = 지하철_노선_등록되어_있음("3호선", "ORANGE")
        lineId1 = createLineResponse1.`as`(LineResponse::class.java).id
        lineId2 = createLineResponse2.`as`(LineResponse::class.java).id
        lineId3 = createLineResponse3.`as`(LineResponse::class.java).id
        stationId1 = createdStationResponse1.`as`(StationResponse::class.java).id
        stationId2 = createdStationResponse2.`as`(StationResponse::class.java).id
        stationId3 = createdStationResponse3.`as`(StationResponse::class.java).id
        stationId4 = createdStationResponse4.`as`(StationResponse::class.java).id
        지하철_노선에_지하철역_등록되어_있음(lineId1, null, stationId1, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId1, stationId1, stationId2, 2, 2)
        지하철_노선에_지하철역_등록되어_있음(lineId2, null, stationId2, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId1, stationId2, stationId3, 2, 1)
        지하철_노선에_지하철역_등록되어_있음(lineId3, null, stationId1, 0, 0)
        지하철_노선에_지하철역_등록되어_있음(lineId3, stationId1, stationId4, 1, 2)
        지하철_노선에_지하철역_등록되어_있음(lineId3, stationId4, stationId3, 2, 2)
        회원_등록되어_있음(EMAIL, PASSWORD, 20)

        // 로그인_되어있음
    }

    @DisplayName("즐겨찾기를 관리한다.")
    @Test
    fun manageMember() {
    }

    companion object {
        const val EMAIL = "email@email.com"
        const val PASSWORD = "password"
    }
}
