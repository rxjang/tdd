package com.example.tdd.ktmoney

class Dollar(
    override val amount: Int,
): Money(amount, currency = "USD") {

    override fun times(multiplier: Int): Money {
        return Money.dollar(amount * multiplier)
    }
}