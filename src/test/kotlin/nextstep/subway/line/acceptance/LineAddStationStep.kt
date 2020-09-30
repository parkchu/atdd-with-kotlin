package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.dto.StationResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object LineAddStationStep {
    fun 노선에_역_등록되어_있음(stationResponse: StationResponse, lineResponse: LineResponse): ExtractableResponse<Response> {
        return 노선에_역_등록_요청(stationResponse, lineResponse)
    }

    fun 노선에_역_등록_요청(stationResponse: StationResponse, lineResponse: LineResponse, preStationId: Long? = null): ExtractableResponse<Response> {
        val params: MutableMap<String, Long?> = HashMap()
        params["lineId"] = lineResponse.id
        params["preStationId"] = preStationId
        params["stationId"] = stationResponse.id
        params["distance"] = 10
        params["time"] = 10
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).`when`()
                .post("/lines/${lineResponse.id}/stations")
                .then()
                .log().all().extract()
    }

    fun 노선에_역_등록됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 노선정보에_역정보_포함됨(response: ExtractableResponse<Response>) {
        assertThat(response.`as`(LineResponse::class.java).stations).isNotEmpty
    }

    fun 노선_마지막에_등록됨(response: ExtractableResponse<Response>, lastStationName: String) {
        val responseBody = response.`as`(LineResponse::class.java)
        assertThat(responseBody.stations).isNotEmpty
        assertThat(responseBody.stations.last().station.name).isEqualTo(lastStationName)
    }

    fun 노선_중간에_등록됨(response: ExtractableResponse<Response>, stations: Map<String, String>) {
        val responseBody = response.`as`(LineResponse::class.java)
        assertThat(responseBody.stations).isNotEmpty
        assertThat(responseBody.stations.first().station.name).isEqualTo(stations["first"])
        assertThat(responseBody.stations.last().station.name).isEqualTo(stations["last"])
        assertThat(responseBody.stations[1].station.name).isEqualTo(stations["second"])
    }
}
