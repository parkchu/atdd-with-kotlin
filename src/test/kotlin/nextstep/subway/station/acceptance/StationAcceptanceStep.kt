package nextstep.subway.station.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.station.dto.StationResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object StationAcceptanceStep {
    fun 지하철역_등록되어_있음(name: String): ExtractableResponse<Response> {
        return 지하철역_생성_요청(name)
    }

    fun 지하철역_생성_요청(name: String): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["name"] = name
        return RestAssured
                .given().log().all().body(params).contentType(MediaType.APPLICATION_JSON_VALUE)
                .`when`().post("/stations")
                .then().log().all().extract()
    }

    fun 지하철역_목록_조회_요청(): ExtractableResponse<Response> {
        return RestAssured
                .given().log().all()
                .`when`()["/stations"]
                .then().log().all().extract()
    }

    fun 지하철역_제거_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured
                .given().log().all().
                `when`().delete(uri)
                .then().log().all().extract()
    }

    fun 지하철역_생성됨(response: ExtractableResponse<*>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.header("Location")).isNotBlank()
    }

    fun 지하철역_생성_실패됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    fun 지하철역_목록_응답됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 지하철역_삭제됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }

    fun 지하철역_목록_포함됨(response: ExtractableResponse<Response>, createdResponses: List<ExtractableResponse<Response>>) {
        val expectedLineIds = createdResponses.map { extractLineId(it) }
        val resultLineIds = response.jsonPath().getList(".", StationResponse::class.java)
                .map { it: StationResponse -> it.id }
        assertThat(resultLineIds).containsAll(expectedLineIds)
    }

    private fun extractLineId(it: ExtractableResponse<Response>) =
            it.header("Location").split("/").toTypedArray()[2].toLong()
}
