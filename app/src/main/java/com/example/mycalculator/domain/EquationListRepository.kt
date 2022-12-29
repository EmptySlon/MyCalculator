package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface EquationListRepository {

    fun getEquationList(): LiveData<List<Equation>>

    fun addEquation(equation: Equation)

}