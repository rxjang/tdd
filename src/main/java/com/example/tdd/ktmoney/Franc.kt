package com.example.tdd.ktmoney

data class Franc(
    private val amount: Int,
) {

    fun times(multiplier: Int): Franc {
        return Franc(amount * multiplier)
    }
}