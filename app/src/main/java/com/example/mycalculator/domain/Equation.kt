package com.example.mycalculator.domain

data class Equation(
    var equation: String,
    var isCorrectEquation: Boolean,
    var answer: String = "",
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}