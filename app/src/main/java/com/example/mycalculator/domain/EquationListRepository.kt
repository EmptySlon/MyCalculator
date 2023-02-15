package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface EquationListRepository {

    fun getEquationList(): LiveData<List<Equation>>

    suspend fun addEquation(equation: Equation)

}