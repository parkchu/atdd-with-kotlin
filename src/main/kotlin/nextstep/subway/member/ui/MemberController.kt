package nextstep.subway.member.ui

import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.member.application.MemberService
import nextstep.subway.member.domain.LoginMember
import nextstep.subway.member.dto.MemberRequest
import nextstep.subway.member.dto.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun findMemberOfMine(request: HttpServletRequest): ResponseEntity<MemberResponse> {
        val context = request.session.getAttribute(SPRING_SECURITY_CONTEXT_KEY) as SecurityContext
        val loginMember = context.authentication!!.principal as LoginMember
        val member = MemberResponse.of(loginMember)
        return ResponseEntity.ok().body(member)
    }

    @PutMapping("/members/{id}")
    fun updateMember(@PathVariable id: Long, @RequestBody param: MemberRequest): ResponseEntity<MemberResponse> {
        memberService.updateMember(id, param)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/members/{id}")
    fun deleteMember(@PathVariable id: Long): ResponseEntity<MemberResponse> {
        memberService.deleteMember(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login/token")
    fun login(): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok().build()
    }
}
