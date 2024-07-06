package com.example.tdd.ktmoney

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Month

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

    @Test
    fun testSimpleAddition() {
        val five = Money.dollar(5)
        val sum: Expression = five + five
        val bank = Bank()
        val reduced = bank.reduce(sum, "USD")
        assertThat(reduced).isEqualTo(Money.dollar(10))
    }

    @Test
    fun testPlusReturnsSum() {
        val five = Money.dollar(5)
        val result = five.plus(five)
        val sum = result as Sum
        assertThat(five).isEqualTo(sum.augend)
        assertThat(five).isEqualTo(sum.addend)
    }

    @Test
    fun testReduceSum() {
        val sum = Sum(Money.dollar(3), Money.dollar(4))
        val bank = Bank()
        val result = bank.reduce(sum, "USD")
        assertThat(Money.dollar(7)).isEqualTo(result)
    }

    @Test
    fun testReduceMoney() {
        val bank = Bank()
        val result: Money = bank.reduce(Money.dollar(1), "USD")
        assertThat(result).isEqualTo(Money.dollar(1))
    }

}