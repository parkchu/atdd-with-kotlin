package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.line.dto.LineResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType


object LineStationAcceptanceStep {
    fun 지하철_노선에_지하철역_등록되어_있음(lineId: Long, preStationId: Long?, stationId: Long) {
        지하철_노선에_지하철역_등록_요청(lineId, preStationId, stationId)
    }

    fun 지하철_노선에_지하철역_등록되어_있음(lineId: Long, preStationId: Long?, stationId: Long, distance: Int, duration: Int) {
        지하철_노선에_지하철역_등록_요청(lineId, preStationId, stationId, distance, duration)
    }

    fun 지하철_노선에_지하철역_등록_요청(lineId: Long, preStationId: Long?, stationId: Long): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["preStationId"] = preStationId.toString() + ""
        params["stationId"] = stationId.toString() + ""
        params["distance"] = "5"
        params["duration"] = "2"
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`()
                .post("/lines/{lineId}/stations", lineId)
                .then().log().all().extract()
    }

    fun 지하철_노선에_지하철역_등록_요청(lineId: Long, preStationId: Long?, stationId: Long, distance: Int, duration: Int): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["preStationId"] = preStationId.toString() + ""
        params["stationId"] = stationId.toString() + ""
        params["distance"] = distance.toString() + ""
        params["duration"] = duration.toString() + ""
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`()
                .post("/lines/{lineId}/stations", lineId)
                .then().log().all().extract()
    }

    fun 지하철_노선에_지하철역_제외_요청(lineId: Long?, stationId: Long?): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
                .`when`()
                .delete("/lines/{lineId}/stations/{stationId}", lineId, stationId)
                .then().log().all().extract()
    }

    fun 지하철_노선에_지하철역_등록됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 지하철_노선_정보_응답됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 지하철_노선에_지하철역_제외됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }

    fun 지하철_노선에_지하철역_제외_실패됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    fun 지하철_노선에_지하철역_제외_확인됨(response: ExtractableResponse<Response?>, stationId: Long) {
        val stationIds = response.`as`(LineResponse::class.java).stations.map { (station) -> station.id }
        assertThat(stationIds).doesNotContain(stationId)
    }

    fun 지하철_노선에_지하철역_순서_정렬됨(response: ExtractableResponse<Response>, expectedStationIds: List<Long>) {
        val (_, _, _, _, _, _, stations) = response.`as`(LineResponse::class.java)
        val stationIds = stations.map { (station) -> station.id }
        assertThat(stationIds).containsExactlyElementsOf(expectedStationIds)
    }
}
