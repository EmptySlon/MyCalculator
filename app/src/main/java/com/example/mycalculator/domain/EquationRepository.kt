package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface EquationRepository {

    fun addChar(appendedChar: Char, cursorPosition: Int, textEquation: String)

    fun deleteChar(cursorPosition: Int)

    fun getEquation(): LiveData<Equation>

    fun calculateResult(textEquation: String)

    fun deleteEquation()

    fun setEquation(newEquation: Equation)
}