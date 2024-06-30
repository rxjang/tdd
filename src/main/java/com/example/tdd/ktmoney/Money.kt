package com.example.tdd.ktmoney

open class Money(
    protected open val amount: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount
    }

    override fun hashCode(): Int {
        return amount
    }
}