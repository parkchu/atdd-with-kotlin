package nextstep.subway.auth.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.SubwayApplication
import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.내_회원_정보_조회_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.로그인_되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.로그인_됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.로그인_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_등록되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_삭제_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_삭제됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_수정_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_수정됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_조회됨
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SubwayApplication::class])
class AuthAcceptanceTest : AcceptanceTest() {
    @DisplayName("Session 로그인 후 내 정보 조회")
    @Test
    fun myInfoWithSession() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE)
        val response: ExtractableResponse<Response> = 내_회원_정보_조회_요청(EMAIL, PASSWORD)
        회원_정보_조회됨(response, EMAIL, AGE)
    }

    @DisplayName("Basic Auth를 통한 로그인 시도")
    @Test
    fun loginWithBasicAuth() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE)
        val response: ExtractableResponse<Response> = 로그인_요청(EMAIL, PASSWORD)
        로그인_됨(response)
    }

    @DisplayName("Bearer Auth")
    @Test
    fun myInfoWithBearerAuth() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE)
        val tokenResponse: TokenResponse = 로그인_되어_있음(EMAIL, PASSWORD)
        val response: ExtractableResponse<Response> = 내_회원_정보_조회_요청(tokenResponse)
        회원_정보_조회됨(response, EMAIL, AGE)
    }

    @DisplayName("내 정보 수정")
    @Test
    fun updateMyInformation() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE)
        val tokenResponse: TokenResponse = 로그인_되어_있음(EMAIL, PASSWORD)
        val updateResponse = 회원_정보_수정_요청(tokenResponse, UPDATE_EMAIL, UPDATE_PASSWORD, UPDATE_AGE)
        회원_정보_수정됨(updateResponse)
    }

    @DisplayName("내 정보 삭제")
    @Test
    fun deleteMyInformation() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE)
        val tokenResponse: TokenResponse = 로그인_되어_있음(EMAIL, PASSWORD)
        val deleteResponse = 회원_삭제_요청(tokenResponse)
        회원_삭제됨(deleteResponse)
    }

    companion object {
        private const val EMAIL = "email@email.com"
        private const val PASSWORD = "password"
        private const val AGE = 20
        private const val UPDATE_EMAIL = "update@email.com"
        private const val UPDATE_PASSWORD = "update_password"
        private const val UPDATE_AGE = 18
    }
}
