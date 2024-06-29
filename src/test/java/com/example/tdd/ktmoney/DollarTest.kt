package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class DollarTest {

    @Test
    fun testMultiplication() {
        val five = Dollar(5)
        var products = five.times(2)
        assertThat(products.amount).isEqualTo(10)
        products = five.times(3)
        assertThat(products.amount).isEqualTo(15)
    }
}