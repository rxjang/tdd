package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class DollarTest {

    @Test
    fun testMultiplication() {
        val five = Money.dollar(5)
        assertThat(Money.dollar(10)).isEqualTo(five.times(2))
        assertThat(Money.dollar(15)).isEqualTo(five.times(3))
    }

    @Test
    fun testFrancMultiplication() {
        val five = Money.franc(5)
        assertThat(five.times(2)).isEqualTo( Money.franc(10))
        assertThat(five.times(3)).isEqualTo( Money.franc(15))
    }

    @Test
    fun testEquality() {
        // Dollar
        assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5))
        assertThat(Money.dollar(5)).isNotEqualTo(Money.dollar(6))
        // Franc
        assertThat( Money.franc(5)).isEqualTo( Money.franc(5))
        assertThat( Money.franc(5)).isNotEqualTo( Money.franc(6))
        // Dollar & Franc
        assertThat( Money.franc(5)).isNotEqualTo(Money.dollar(5))
    }

    @Test
    fun testCurrency() {
        assertThat(Money.dollar(1).currency).isEqualTo("USD");
        assertThat(Money.franc(1).currency).isEqualTo("CHF");
    }
}