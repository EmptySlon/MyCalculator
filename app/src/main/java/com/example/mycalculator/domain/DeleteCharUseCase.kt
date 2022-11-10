package com.example.mycalculator.domain

class DeleteCharUseCase(private val equationRepository: EquationRepository) {
    fun deleteChar(equation: Equation) {
        equationRepository.deleteChar(equation)
    }
}