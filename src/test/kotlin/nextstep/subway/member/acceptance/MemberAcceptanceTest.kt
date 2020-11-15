package nextstep.subway.member.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_등록되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_삭제_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_삭제됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_생성됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_생성을_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_수정_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_수정됨
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_조회_요청
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_정보_조회됨
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MemberAcceptanceTest : AcceptanceTest() {
    @DisplayName("회원가입을 한다.")
    @Test
    fun createMember() {
        // when
        val response: ExtractableResponse<Response> = 회원_생성을_요청(EMAIL, PASSWORD, AGE)

        // then
        회원_생성됨(response)
    }

    @Test
    @DisplayName("회원 정보를 조회한다.")
    fun getMember() {
        // given
        val createResponse: ExtractableResponse<Response> = 회원_등록되어_있음(EMAIL, PASSWORD, AGE)

        // when
        val response: ExtractableResponse<Response> = 회원_정보_조회_요청(createResponse)

        // then
        회원_정보_조회됨(response, EMAIL, AGE)
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    fun updateMember() {
        // given
        val createResponse: ExtractableResponse<Response> = 회원_등록되어_있음(EMAIL, PASSWORD, AGE)

        // when
        val response: ExtractableResponse<Response> = 회원_정보_수정_요청(createResponse, "new" + EMAIL, "new" + PASSWORD, AGE)

        // then
        회원_정보_수정됨(response)
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    fun deleteMember() {
        // given
        val createResponse: ExtractableResponse<Response> = 회원_등록되어_있음(EMAIL, PASSWORD, AGE)

        // when
        val response: ExtractableResponse<Response> = 회원_삭제_요청(createResponse)

        // then
        회원_삭제됨(response)
    }

    @DisplayName("회원 정보를 관리한다.")
    @Test
    fun manageMember() {
        // when
        val response = 회원_생성을_요청(EMAIL, PASSWORD, AGE)

        // then
        회원_생성됨(response)

        // when
        val response2 = 회원_정보_조회_요청(response)

        // then
        회원_정보_조회됨(response2, EMAIL, AGE)

        // when
        val response3 = 회원_정보_수정_요청(response, "new$EMAIL", "new$PASSWORD", 18)

        // then
        회원_정보_수정됨(response3)

        // when
        val response4 = 회원_삭제_요청(response)

        // then
        회원_삭제됨(response4)
    }

    companion object {
        const val EMAIL = "email@email.com"
        const val PASSWORD = "password"
        const val AGE = 20
    }
}
