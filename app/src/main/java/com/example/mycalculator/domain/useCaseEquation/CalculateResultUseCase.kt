package com.example.mycalculator.domain.useCaseEquation

import com.example.mycalculator.domain.EquationRepository

class CalculateResultUseCase(private val equationRepository: EquationRepository) {
    fun calculateResult(textEquation: String) {
       return  equationRepository.calculateResult(textEquation)
    }
}