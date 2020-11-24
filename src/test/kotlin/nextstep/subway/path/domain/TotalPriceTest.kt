package nextstep.subway.path.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TotalPriceTest {
    @Test
    fun getBasicTotalPrice() {
        val distance = 5

        val price = TotalPrice.get(distance)

        assertThat(price).isEqualTo(1250)
    }

    @Test
    fun getTotalPriceOver10km() {
        val distance = 15

        val price = TotalPrice.get(distance)

        assertThat(price).isEqualTo(1350)
    }

    @Test
    fun getTotalPriceOver50km() {
        val distance = 58

        val price = TotalPrice.get(distance)

        assertThat(price).isEqualTo(2150)
    }

    @Test
    fun getTotalPriceAge6() {
        val distance = 5
        val age = 6

        val price = TotalPrice.get(distance, age = age)

        assertThat(price).isEqualTo(800)
    }

    @Test
    fun getTotalPriceAge18() {
        val distance = 5
        val age = 18

        val price = TotalPrice.get(distance, age = age)

        assertThat(price).isEqualTo(1070)
    }
}
