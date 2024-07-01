package com.example.tdd.ktmoney

class Franc(
    override val amount: Int,
): Money(amount) {

    override fun times(multiplier: Int): Money {
        return Franc(amount * multiplier)
    }
}