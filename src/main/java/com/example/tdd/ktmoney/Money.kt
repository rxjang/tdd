package com.example.tdd.ktmoney

import java.time.Month

class Money(
    val amount: Int,
    val currency: String
): Expression {

    companion object {
        fun dollar(amount: Int): Money = Money(amount, "USD")

        fun franc(amount: Int): Money = Money(amount, "CHF")
    }

    operator fun plus(addend: Money): Expression {
        return Money(this.amount + addend.amount, currency)
    }

    operator fun times(multiplier: Int): Money {
        return Money(amount * multiplier, currency)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount && this.currency == other.currency
    }

    override fun hashCode(): Int {
        return amount
    }

    override fun toString(): String {
        return "Money(amount=$amount, currency='$currency')"
    }
}