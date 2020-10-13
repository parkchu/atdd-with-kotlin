package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("지하철 노선에서 역 제외 기능")
class LineDeleteStationTest: AcceptanceTest() {
    var stationId: Long = 0
    var lineId: Long = 0
    private val uri: String
        get() = "/lines/$lineId/stations/$stationId"

    @BeforeEach
    fun backGround() {
        stationId = 등록한_지하철역정보_요청("카츄역").id
        lineId = 등록한_노선정보_요청(name = "주한선", color = "bg-red-600", intervalTime = "5").id
        노선에_역_등록되어_있음(stationId, lineId)
    }

    @DisplayName("지하철 노선에 등록된 마지막 역을 제외한다.")
    @Test
    fun deleteStationOfLine() {
        // When
        val response = RestAssured
                .given().log().all().
                `when`().delete(uri)
                .then().log().all().extract()

        // Given
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        // When

    }
}
