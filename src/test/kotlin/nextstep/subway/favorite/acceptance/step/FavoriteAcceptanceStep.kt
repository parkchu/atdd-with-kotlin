package nextstep.subway.favorite.acceptance.step

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.auth.dto.TokenResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object FavoriteAcceptanceStep {

    fun 즐겨찾기_생성을_요청(source: Long, target: Long, token: TokenResponse): ExtractableResponse<Response> {
        val params: MutableMap<String, String> = HashMap()
        params["source"] = source.toString() + ""
        params["target"] = target.toString() + ""
        return RestAssured.given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`().post("/favorites")
                .then().log().all().extract()
    }

    fun 즐겨찾기_목록_조회_요청(token: TokenResponse): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/favorites"]
                .then().log().all().extract()
    }

    fun 즐겨찾기_삭제_요청(response: ExtractableResponse<Response>, token: TokenResponse): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured.given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .`when`()
                .delete(uri)
                .then().log().all().extract()
    }

    fun 즐겨찾기_생성됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 즐겨찾기_목록_조회됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 즐겨찾기_삭제됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }
}
