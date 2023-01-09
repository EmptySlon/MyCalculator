package com.example.mycalculator.domain

class CalculateResultUseCase(private val equationRepository: EquationRepository) {
    fun calculateResult(textEquation: String) {
       return  equationRepository.calculateResult(textEquation)
    }
}