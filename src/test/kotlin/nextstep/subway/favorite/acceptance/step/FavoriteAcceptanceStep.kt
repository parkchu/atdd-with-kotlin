package nextstep.subway.favorite.acceptance.step

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object FavoriteAcceptanceStep {
    fun 즐겨찾기_등록되어_있음(source: Long, target: Long): ExtractableResponse<Response> {
        return 즐겨찾기_생성을_요청(source, target)
    }

    fun 즐겨찾기_생성을_요청(source: Long, target: Long): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["source"] = source.toString() + ""
        params["target"] = target.toString() + ""
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`().post("/favorites")
                .then().log().all().extract()
    }

    fun 즐겨찾기_목록_조회_요청(): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/favorites"]
                .then().log().all().extract()
    }

    fun 즐겨찾기_삭제_요청(response: ExtractableResponse<Response?>): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured.given().log().all()
                .`when`()
                .delete(uri)
                .then().log().all().extract()
    }

    fun 즐겨찾기_생성됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 즐겨찾기_목록_조회됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 즐겨찾기_삭제됨(response: ExtractableResponse<Response?>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }
}
