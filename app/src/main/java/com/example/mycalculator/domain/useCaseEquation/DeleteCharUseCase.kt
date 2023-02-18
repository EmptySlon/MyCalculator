package com.example.mycalculator.domain.useCaseEquation

import com.example.mycalculator.domain.EquationRepository


class DeleteCharUseCase(private val equationRepository: EquationRepository) {
    fun deleteChar(cursorPosition: Int) {
        equationRepository.deleteChar(cursorPosition)
    }
}