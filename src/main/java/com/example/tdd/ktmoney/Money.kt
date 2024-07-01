package com.example.tdd.ktmoney

import java.time.Month

open class Money(
    open val amount: Int,
    val currency: String
) {

    companion object {
        fun dollar(amount: Int): Money = Dollar(amount)

        fun franc(amount: Int): Money = Franc(amount)
    }

    fun times(multiplier: Int): Money {
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