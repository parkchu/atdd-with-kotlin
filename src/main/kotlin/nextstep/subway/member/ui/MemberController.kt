package nextstep.subway.member.ui

import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import nextstep.subway.auth.ui.interceptor.authentication.TokenAuthenticationInterceptor.Companion.TOKEN_KEY
import nextstep.subway.member.application.MemberService
import nextstep.subway.member.domain.LoginMember
import nextstep.subway.member.dto.MemberRequest
import nextstep.subway.member.dto.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.net.URI
import javax.servlet.http.HttpServletRequest


@RestController
class MemberController(private val memberService: MemberService) {
    @PostMapping("/members")
    fun createMember(@RequestBody request: MemberRequest): ResponseEntity<*> {
        val (id) = memberService.createMember(request)
        return ResponseEntity.created(URI.create("/members/$id")).build<Any>()
    }

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable id: Long): ResponseEntity<MemberResponse> {
        val member = memberService.findMember(id)
        return ResponseEntity.ok().body(member)
    }

    @GetMapping("/members/me")
    fun findMemberOfMine(request: HttpServletRequest, loginMember: LoginMember): ResponseEntity<MemberResponse> {
        val member = MemberResponse.of(loginMember)
        return ResponseEntity.ok().body(member)
    }

    @PutMapping("/members/me")
    fun updateMember(@RequestBody param: MemberRequest, loginMember: LoginMember): ResponseEntity<MemberResponse> {
        memberService.updateMember(loginMember.id, param)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/members/me")
    fun deleteMember(loginMember: LoginMember): ResponseEntity<MemberResponse> {
        memberService.deleteMember(loginMember.id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login/token")
    fun login(request: HttpServletRequest): ResponseEntity<TokenResponse> {
        val token = request.session.getAttribute(TOKEN_KEY) as TokenResponse
        return ResponseEntity.ok().body(token)
    }
}
