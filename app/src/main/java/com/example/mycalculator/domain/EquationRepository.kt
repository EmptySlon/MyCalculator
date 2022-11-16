package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface EquationRepository {

    fun addChar(appendedChar: Char, cursorPosition: Int)

    fun deleteChar(cursorPosition: Int)

    fun getEquation(): LiveData<Equation>

    fun calculateResult()

    fun deleteEquation()
}