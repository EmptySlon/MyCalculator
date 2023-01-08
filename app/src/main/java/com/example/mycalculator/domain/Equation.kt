package com.example.mycalculator.domain

data class Equation(
    var equation: String,
    var isCorrectEquation: Boolean,
    var answer: String = "",
)