package nextstep.subway.member.acceptance.step

import io.restassured.RestAssured
import io.restassured.authentication.FormAuthConfig
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.member.dto.MemberResponse
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object MemberAcceptanceStep {
    const val USERNAME_FIELD = "username"
    const val PASSWORD_FIELD = "password"

    fun 회원_등록되어_있음(email: String, password: String, age: Int): ExtractableResponse<Response> {
        return 회원_생성을_요청(email, password, age)
    }

    fun 로그인_되어_있음(email: String, password: String): TokenResponse {
        val response = 로그인_요청(email, password)
        return response.`as`(TokenResponse::class.java)
    }

    fun 로그인_요청(email: String, password: String): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()
                .post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract()
    }

    fun 회원_생성을_요청(email: String, password: String, age: Int): ExtractableResponse<Response> {
        val params: MutableMap<String, String?> = HashMap()
        params["email"] = email
        params["password"] = password
        params["age"] = age.toString() + ""
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`()
                .post("/members")
                .then().log().all()
                .extract()
    }

    fun 회원_정보_조회_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()[uri]
                .then().log().all().extract()
    }

    fun 회원_정보_수정_요청(response: ExtractableResponse<Response>, email: String, password: String, age: Int): ExtractableResponse<Response> {
        val uri = response.header("Location")
        val params: MutableMap<String, String> = HashMap()
        params["email"] = email
        params["password"] = password
        params["age"] = age.toString() + ""
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params).
                `when`()
                .put(uri)
                .then().log().all().extract()
    }

    fun 회원_삭제_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
        val uri = response.header("Location")
        return RestAssured.given().log().all()
                .`when`()
                .delete(uri)
                .then().log().all().extract()
    }

    fun 내_회원_정보_조회_요청(email: String, password: String): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
                .auth()
                .form(email, password, FormAuthConfig("/login/session", USERNAME_FIELD, PASSWORD_FIELD))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/members/me"]
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract()
    }

    fun 내_회원_정보_조회_요청(tokenResponse: TokenResponse): ExtractableResponse<Response> {
        return RestAssured.given().log().all().auth()
                .oauth2(tokenResponse.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/members/me"]
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract()
    }

    fun 로그인_됨(response: ExtractableResponse<Response>) {
        val tokenResponse: TokenResponse = response.`as`(TokenResponse::class.java)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(tokenResponse.accessToken).isNotBlank()
    }

    fun 회원_생성됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 회원_정보_조회됨(response: ExtractableResponse<Response>, email: String?, age: Int) {
        val memberResponse: MemberResponse = response.`as`(MemberResponse::class.java)
        assertThat(memberResponse.id).isNotNull()
        assertThat(memberResponse.email).isEqualTo(email)
        assertThat(memberResponse.age).isEqualTo(age)
    }

    fun 회원_정보_수정됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 회원_삭제됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }
}
