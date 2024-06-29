package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class DollarTest {

    @Test
    fun testMultiplication() {
        val five = Dollar(5)
        five.times(2)
        assertThat(five.amount).isEqualTo(10)
    }
}