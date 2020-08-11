package nextstep.subway

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {
    @GetMapping(value = ["/"], produces = [MediaType.TEXT_HTML_VALUE])
    fun index(): String {
        return "index"
    }

    @GetMapping(value = ["/stations"], produces = [MediaType.TEXT_HTML_VALUE])
    fun stations(): String {
        return "index"
    }

    @GetMapping(value = ["/lines"], produces = [MediaType.TEXT_HTML_VALUE])
    fun lines(): String {
        return "index"
    }

    @GetMapping(value = ["/edges"], produces = [MediaType.TEXT_HTML_VALUE])
    fun lineStations(): String {
        return "index"
    }

    @GetMapping(value = ["/maps"], produces = [MediaType.TEXT_HTML_VALUE])
    fun maps(): String {
        return "index"
    }

    @GetMapping(value = ["/path"], produces = [MediaType.TEXT_HTML_VALUE])
    fun path(): String {
        return "index"
    }

    @GetMapping(value = ["/login"], produces = [MediaType.TEXT_HTML_VALUE])
    fun login(): String {
        return "index"
    }

    @GetMapping(value = ["/join"], produces = [MediaType.TEXT_HTML_VALUE])
    fun join(): String {
        return "index"
    }

    @GetMapping(value = ["/mypage"], produces = [MediaType.TEXT_HTML_VALUE])
    fun mypage(): String {
        return "index"
    }

    @GetMapping(value = ["/mypage/edit"], produces = [MediaType.TEXT_HTML_VALUE])
    fun mypageEdit(): String {
        return "index"
    }

    @GetMapping(value = ["/favorites"], produces = [MediaType.TEXT_HTML_VALUE])
    fun favorites(): String {
        return "index"
    }
}
