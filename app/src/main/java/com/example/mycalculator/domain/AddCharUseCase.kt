package com.example.mycalculator.domain

class AddCharUseCase(private val equationRepository: EquationRepository) {
    fun addChar(equation: Equation) {
        equationRepository.addChar(equation)
    }
}