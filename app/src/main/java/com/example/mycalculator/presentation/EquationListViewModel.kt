package com.example.mycalculator.presentation

import androidx.lifecycle.ViewModel
import com.example.mycalculator.data.EquationListRepositoryImpl
import com.example.mycalculator.domain.AddEquationUseCase
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.GetEquationListUseCase

class EquationListViewModel : ViewModel() {
    private val repository = EquationListRepositoryImpl

    private val getEquationListUseCase = GetEquationListUseCase(repository)
    private val addEquationListUseCase = AddEquationUseCase(repository)

    val equationList = getEquationListUseCase.getEquationList()

    fun addEquationList(equation: Equation) {
        addEquationListUseCase.addEquation(equation)
    }
}