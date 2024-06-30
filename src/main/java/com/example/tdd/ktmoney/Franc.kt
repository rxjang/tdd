package com.example.tdd.ktmoney

class Franc(
    override val amount: Int,
): Money(amount) {

    fun times(multiplier: Int): Franc {
        return Franc(amount * multiplier)
    }
}