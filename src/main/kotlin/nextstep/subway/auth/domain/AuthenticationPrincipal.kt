package nextstep.subway.auth.domain

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class AuthenticationPrincipal
