package com.example.mycalculator.domain

class CalculateResultUseCase(private val equationRepository: EquationRepository) {
    fun calculateResult(equation: Equation) {
       return  equationRepository.calculateResult(equation)
    }
}