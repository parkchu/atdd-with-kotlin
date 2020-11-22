package nextstep.subway.favorite.ui

import nextstep.subway.favorite.application.FavoriteService
import nextstep.subway.favorite.dto.FavoriteRequest
import nextstep.subway.favorite.dto.FavoriteResponse
import nextstep.subway.member.domain.LoginMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class FavoriteController @Autowired constructor(
        private val favoriteService: FavoriteService
) {
    @PostMapping("/favorites")
    fun createFavorite2(@RequestBody request: FavoriteRequest?, loginMember: LoginMember): ResponseEntity<*> {
        val favoriteId = favoriteService.createFavorite(request!!, loginMember.id)
        return ResponseEntity
                .created(URI.create("/favorites/$favoriteId"))
                .build<Any>()
    }

    @GetMapping("/favorites")
    fun findMyFavorites(loginMember: LoginMember): ResponseEntity<List<FavoriteResponse>> {
        val favorites = favoriteService.findMyFavorites(loginMember.id)
        return ResponseEntity.ok().body(favorites)
    }

    @DeleteMapping("/favorites/{id}")
    fun deleteFavorite(@PathVariable id: Long, loginMember: LoginMember): ResponseEntity<*> {
        favoriteService.deleteFavorite(id)
        return ResponseEntity.noContent().build<Any>()
    }
}
