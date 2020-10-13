package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.노선_조회_요청
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("지하철 노선에서 역 제외 기능")
class LineDeleteStationTest: AcceptanceTest() {
    var station1Id: Long = 0
    var station2Id: Long = 0
    var station3Id: Long = 0
    var lineId: Long = 0
    private val uri: String
        get() = "/lines/$lineId/stations"

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
        val response = RestAssured
                .given().log().all().
                `when`().delete("$uri/$station2Id")
                .then().log().all().extract()

        // Given
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        // When
        val lineResponse = 노선_조회_요청(lineId).`as`(LineResponse::class.java)

        // Given
        assertThat(lineResponse.stations.find { it.station.id == station2Id }).isNull()
        assertThat(lineResponse.stations.first().station.id).isEqualTo(station1Id)
        assertThat(lineResponse.stations.last().station.id).isEqualTo(station3Id)
    }

    @DisplayName("지하철 노선에 등록된 중간 역을 제외한다.")
    @Test
    fun deleteStationOfLine2() {
        // When
        val response = RestAssured
                .given().log().all().
                `when`().delete("$uri/$station3Id")
                .then().log().all().extract()

        // Given
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        // When
        val lineResponse = 노선_조회_요청(lineId).`as`(LineResponse::class.java)

        // Given
        assertThat(lineResponse.stations.find { it.station.id == station3Id }).isNull()
        assertThat(lineResponse.stations.first().station.id).isEqualTo(station1Id)
        assertThat(lineResponse.stations.last().station.id).isEqualTo(station2Id)
        assertThat(lineResponse.stations.last().preStationId).isEqualTo(station1Id)
    }
}
