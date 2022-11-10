package com.example.mycalculator.domain

interface EquationRepository {

    fun addChar(equation: Equation)

    fun deleteChar(equation: Equation)

    fun getEquation(): Equation

    fun calculateResult(equation: Equation): String
}