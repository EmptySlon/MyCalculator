package com.example.mycalculator.domain

class CalculateResultUseCase(private val equationRepository: EquationRepository) {
    fun calculateResult() {
       return  equationRepository.calculateResult()
    }
}