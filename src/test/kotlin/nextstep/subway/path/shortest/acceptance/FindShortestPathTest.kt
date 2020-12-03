package nextstep.subway.path.shortest.acceptance

import io.restassured.RestAssured
import nextstep.subway.AcceptanceTest
import nextstep.subway.favorite.acceptance.FavoriteAcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceStep.등록한_노선정보_요청
import nextstep.subway.line.acceptance.LineAddStationStep.노선에_역_등록되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.로그인_되어_있음
import nextstep.subway.member.acceptance.step.MemberAcceptanceStep.회원_등록되어_있음
import nextstep.subway.path.shortest.dto.PathResponse
import nextstep.subway.station.acceptance.StationAcceptanceStep.등록한_지하철역정보_요청
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("지하철 경로 검색")
class FindShortestPathTest : AcceptanceTest() {

    @DisplayName("두 역의 최단 거리 경로를 조회")
    @Test
    fun findShortestPath() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val station4 = 등록한_지하철역정보_요청("영정역")
        val station5 = 등록한_지하철역정보_요청("예은역")
        val line = 등록한_노선정보_요청("1호선", "bg-red-600", "5", 500)
        노선에_역_등록되어_있음(station1.id, line.id)
        노선에_역_등록되어_있음(station2.id, line.id)
        노선에_역_등록되어_있음(station3.id, line.id)
        노선에_역_등록되어_있음(station4.id, line.id)
        노선에_역_등록되어_있음(station5.id, line.id)

        // When
        val response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station5.id}&type=DISTANCE"]
                .then().log().all().extract()

        // Then
        val pathResponse = response.`as`(PathResponse::class.java)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(pathResponse.distance).isEqualTo(40)
        assertThat(pathResponse.duration).isEqualTo(40)
        assertThat(pathResponse.stations).hasSize(5)
        assertThat(pathResponse.fare).isEqualTo(2_350)
    }

    @DisplayName("두 역의 최단 시간 경로를 조회")
    @Test
    fun findShortestPath2() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val station4 = 등록한_지하철역정보_요청("영정역")
        val station5 = 등록한_지하철역정보_요청("예은역")
        val line = 등록한_노선정보_요청("1호선", "bg-red-600", "5")
        노선에_역_등록되어_있음(station1.id, line.id)
        노선에_역_등록되어_있음(station2.id, line.id)
        노선에_역_등록되어_있음(station3.id, line.id)
        노선에_역_등록되어_있음(station4.id, line.id)
        노선에_역_등록되어_있음(station5.id, line.id)

        // When
        val response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station5.id}&type=DURATION"]
                .then().log().all().extract()

        // Then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.`as`(PathResponse::class.java).distance).isEqualTo(40)
        assertThat(response.`as`(PathResponse::class.java).duration).isEqualTo(40)
        assertThat(response.`as`(PathResponse::class.java).stations).hasSize(5)
    }

    @DisplayName("경로 중 추가요금이 있는 노선을 환승 하여 이용 할 경우 가장 높은 금액의 추가 요금만 적용")
    @Test
    fun findShortestPath3() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val station4 = 등록한_지하철역정보_요청("영정역")
        val station5 = 등록한_지하철역정보_요청("예은역")
        val line = 등록한_노선정보_요청("1호선", "bg-red-600", "5", 500)
        val line2 = 등록한_노선정보_요청("2호선", "bg-red-600", "5", 300)
        노선에_역_등록되어_있음(station1.id, line.id)
        노선에_역_등록되어_있음(station2.id, line.id)
        노선에_역_등록되어_있음(station3.id, line.id)
        노선에_역_등록되어_있음(station3.id, line2.id)
        노선에_역_등록되어_있음(station4.id, line2.id)
        노선에_역_등록되어_있음(station5.id, line2.id)
        회원_등록되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD, 6)
        val token = 로그인_되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD)

        // When
        val response = RestAssured
                .given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station5.id}&type=DISTANCE"]
                .then().log().all().extract()

        // Then
        val pathResponse = response.`as`(PathResponse::class.java)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(pathResponse.distance).isEqualTo(40)
        assertThat(pathResponse.duration).isEqualTo(40)
        assertThat(pathResponse.stations).hasSize(5)
        assertThat(pathResponse.fare).isEqualTo(1_350)
    }

    @DisplayName("가장 빠른 도착 경로 조회")
    @Test
    fun findFastestPath() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val line = 등록한_노선정보_요청("1호선", "bg-red-600", "30", 500)
        val line2 = 등록한_노선정보_요청("2호선", "bg-red-600", "5", 300)
        노선에_역_등록되어_있음(station1.id, line.id, 5)
        노선에_역_등록되어_있음(station2.id, line.id, 5)
        노선에_역_등록되어_있음(station3.id, line.id, 5)
        노선에_역_등록되어_있음(station1.id, line2.id, 10)
        노선에_역_등록되어_있음(station2.id, line2.id, 10)
        노선에_역_등록되어_있음(station3.id, line2.id, 10)
        회원_등록되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD, 6)
        val token = 로그인_되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD)

        // When
        val fastestResponse = RestAssured
                .given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station3.id}&type=ARRIVAL_TIME&time=202007220531"]
                .then().log().all().extract()
        val shortestResponse = RestAssured
                .given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station3.id}&type=DURATION"]
                .then().log().all().extract()

        // Then
        val pathResponse = fastestResponse.`as`(PathResponse::class.java)
        assertThat(fastestResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(pathResponse.distance).isEqualTo(20)
        assertThat(pathResponse.duration).isEqualTo(24)
        assertThat(pathResponse.stations).hasSize(3)
        assertThat(pathResponse.fare).isEqualTo(1_050)
        val shortestPath = shortestResponse.`as`(PathResponse::class.java)
        assertThat(shortestPath.duration).isEqualTo(10)
        assertThat(shortestPath.stations).hasSize(3)
    }

    @DisplayName("가장 빠른 도착 경로 조회")
    @Test
    fun findFastestPath2() {
        // Given
        val station1 = 등록한_지하철역정보_요청("카츄역")
        val station2 = 등록한_지하철역정보_요청("주한역")
        val station3 = 등록한_지하철역정보_요청("재성역")
        val line2 = 등록한_노선정보_요청("2호선", "bg-red-600", "5", 300)
        노선에_역_등록되어_있음(station1.id, line2.id, 10)
        노선에_역_등록되어_있음(station2.id, line2.id, 10)
        노선에_역_등록되어_있음(station3.id, line2.id, 10)
        회원_등록되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD, 6)
        val token = 로그인_되어_있음(FavoriteAcceptanceTest.EMAIL, FavoriteAcceptanceTest.PASSWORD)

        // When
        val fastestResponse = RestAssured
                .given().log().all()
                .auth()
                .oauth2(token.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/paths/?source=${station1.id}&target=${station3.id}&type=ARRIVAL_TIME&time=202007220531"]
                .then().log().all().extract()

        // Then
        val pathResponse = fastestResponse.`as`(PathResponse::class.java)
        assertThat(fastestResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(pathResponse.distance).isEqualTo(20)
        assertThat(pathResponse.duration).isEqualTo(24)
        assertThat(pathResponse.stations).hasSize(3)
        assertThat(pathResponse.fare).isEqualTo(1_050)
    }
}
