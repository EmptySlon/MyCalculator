package com.example.mycalculator.domain

class AddEquationUseCase (private val equationListRepository: EquationListRepository) {
    fun addEquation(equation: Equation) {
        equationListRepository.addEquation(equation)
    }
}