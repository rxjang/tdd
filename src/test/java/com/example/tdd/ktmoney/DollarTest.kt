package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class DollarTest {

    @Test
    fun testMultiplication() {
        val five = Dollar(5)
        assertThat(five.times(2)).isEqualTo(Dollar(10))
        assertThat(five.times(3)).isEqualTo(Dollar(15))
    }

    @Test
    fun testEquality() {
        assertThat(Dollar(5)).isEqualTo(Dollar(5))
        assertThat(Dollar(5)).isNotEqualTo(Dollar(6))
    }
}