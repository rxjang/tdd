package com.example.tdd.ktmoney

class Franc(
    override val amount: Int,
): Money(amount, currency = "CHF") {

    override fun times(multiplier: Int): Money {
        return Money.franc(amount * multiplier)
    }
}