package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface EquationRepository {

    fun addChar(appendedChar: Char, cursorPosition: Int)

    fun deleteChar(char: Char, cursorPosition: Int)

    fun getEquation(): LiveData<Equation>

    fun calculateResult(equation: Equation)
}