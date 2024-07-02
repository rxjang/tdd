package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class DollarTest {

    @Test
    fun testMultiplication() {
        val five = Money.dollar(5)
        assertThat(Money.dollar(10)).isEqualTo(five * 2)
        assertThat(Money.dollar(15)).isEqualTo(five * 3)
    }

    @Test
    fun testEquality() {
        // Dollar
        assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5))
        assertThat(Money.dollar(5)).isNotEqualTo(Money.dollar(6))
        // Dollar & Franc
        assertThat(Money.franc(5)).isNotEqualTo(Money.dollar(5))
    }

    @Test
    fun testCurrency() {
        assertThat(Money.dollar(1).currency).isEqualTo("USD");
        assertThat(Money.franc(1).currency).isEqualTo("CHF");
    }

}