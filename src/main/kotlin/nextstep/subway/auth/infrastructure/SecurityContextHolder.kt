package nextstep.subway.auth.infrastructure

object SecurityContextHolder {
    const val SPRING_SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT"

    private val contextHolder: ThreadLocal<SecurityContext?> = ThreadLocal()

    fun clearContext() {
        contextHolder.remove()
    }

    var context: SecurityContext
        get() {
            var ctx = contextHolder.get()
            if (ctx == null) {
                ctx = createEmptyContext()
                contextHolder.set(ctx)
            }
            return ctx
        }
        set(context) {
            if (context != null) {
                contextHolder.set(context)
            }
        }

    fun createEmptyContext(): SecurityContext {
        return SecurityContext()
    }
}
