package nextstep.subway.favorite.ui

import nextstep.subway.favorite.application.FavoriteService
import nextstep.subway.favorite.dto.FavoriteRequest
import nextstep.subway.favorite.dto.FavoriteResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class FavoriteController @Autowired constructor(
        private val favoriteService: FavoriteService
) {
    @PostMapping("/favorites")
    fun createFavorite(@RequestBody request: FavoriteRequest?): ResponseEntity<*> {
        favoriteService.createFavorite(request!!)
        return ResponseEntity
                .created(URI.create("/favorites/" + 1L))
                .build<Any>()
    }

    @get:GetMapping("/favorites")
    val favorites: ResponseEntity<List<FavoriteResponse?>>
        get() {
            val favorites = favoriteService.findFavorites()
            return ResponseEntity.ok().body(favorites)
        }

    @DeleteMapping("/favorites/{id}")
    fun deleteFavorite(@PathVariable id: Long?): ResponseEntity<*> {
        favoriteService.deleteFavorite(id!!)
        return ResponseEntity.noContent().build<Any>()
    }
}
