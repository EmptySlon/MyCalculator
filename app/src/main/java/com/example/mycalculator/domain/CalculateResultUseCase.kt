package com.example.mycalculator.domain

class CalculateResultUseCase(private val equationRepository: EquationRepository) {
    fun calculateResult(equation: Equation): String {
       return  equationRepository.calculateResult(equation)
    }
}