package com.example.tdd.ktmoney

import java.time.Month

abstract class Money(
    protected open val amount: Int,
    val currency: String
) {

    companion object {
        fun dollar (amount: Int): Money = Dollar(amount)

        fun franc (amount: Int): Money = Franc(amount)
    }

    abstract fun times(multiplier: Int): Money

    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount && other.javaClass == this.javaClass
    }

    override fun hashCode(): Int {
        return amount
    }
}