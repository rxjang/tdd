package com.example.tdd.money;

public class Bank {

    Money reduce(Expression source, String to) {
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}