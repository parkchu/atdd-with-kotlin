package nextstep.subway.map.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.map.dto.MapResponse
import nextstep.subway.station.dto.StationResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object MapLookupStep {
    fun 노선도_조회_요청(eTag: String = ""): ExtractableResponse<Response> {
        return RestAssured
                .given()
                .log().all()
                .header("If-None-Match", eTag)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()
                .get("/maps")
                .then()
                .header("ETag", CoreMatchers.notNullValue())
                .log().all().extract()
    }

    fun 노선도_조회됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 노선별_지하철역_순서_정렬됨(response: ExtractableResponse<Response>, stations: List<StationResponse>) {
        val mapResponse = response.`as`(MapResponse::class.java)
        assertThat(mapResponse.lineResponses[0].stations.first().station.id).isEqualTo(stations[0].id)
        assertThat(mapResponse.lineResponses[0].stations[1].station.id).isEqualTo(stations[1].id)
        assertThat(mapResponse.lineResponses[0].stations.last().station.id).isEqualTo(stations[2].id)
        assertThat(mapResponse.lineResponses[1].stations.first().station.id).isEqualTo(stations[0].id)
        assertThat(mapResponse.lineResponses[1].stations[1].station.id).isEqualTo(stations[3].id)
        assertThat(mapResponse.lineResponses[1].stations.last().station.id).isEqualTo(stations[4].id)
    }

    fun 노선도_조회_실패됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_MODIFIED.value())
    }
}
