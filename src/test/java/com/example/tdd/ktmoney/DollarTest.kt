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
    fun testFrancMultiplication() {
        val five = Franc(5)
        assertThat(five.times(2)).isEqualTo(Franc(10))
        assertThat(five.times(3)).isEqualTo(Franc(15))
    }

    @Test
    fun testEquality() {
        // Dollar
        assertThat(Dollar(5)).isEqualTo(Dollar(5))
        assertThat(Dollar(5)).isNotEqualTo(Dollar(6))
        // Franc
        assertThat(Franc(5)).isEqualTo(Franc(5))
        assertThat(Franc(5)).isNotEqualTo(Franc(6))
        // Dollar & Franc
        assertThat(Franc(5)).isEqualTo(Dollar(5))
    }
}