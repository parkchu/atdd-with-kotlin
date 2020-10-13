package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.line.dto.LineResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

object LineDeleteStationStep {
    fun 노선에_등록된_역_제외_요청(lineId: Long, stationId: Long): ExtractableResponse<Response> {
        return RestAssured
                .given().log().all().
                `when`().delete("/lines/$lineId/stations/$stationId")
                .then().log().all().extract()
    }

    fun 노선에_등록된_역_제외됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 노선에_역_제외_확인됨(lineResponse: LineResponse, stationId: Long) {
        assertThat(lineResponse.stations.find { it.station.id == stationId }).isNull()
    }
    fun 노선에_역_순서_정렬됨(lineResponse: LineResponse, firstStationId: Long, lastStationId: Long) {
        assertThat(lineResponse.stations.first().station.id).isEqualTo(firstStationId)
        assertThat(lineResponse.stations.last().station.id).isEqualTo(lastStationId)
        assertThat(lineResponse.stations.last().preStationId).isEqualTo(firstStationId)
    }

    fun 노선에_역_제외_실패됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }
}
