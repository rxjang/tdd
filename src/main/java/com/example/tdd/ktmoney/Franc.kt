package com.example.tdd.ktmoney

class Franc(
    override val amount: Int,
): Money(amount, currency = "CHF") {

}