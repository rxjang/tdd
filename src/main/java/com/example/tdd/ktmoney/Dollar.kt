package com.example.tdd.ktmoney

class Dollar(
    override val amount: Int,
): Money(amount, currency = "USD") {

}