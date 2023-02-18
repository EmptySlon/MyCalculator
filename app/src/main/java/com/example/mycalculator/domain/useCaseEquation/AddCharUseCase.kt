package com.example.mycalculator.domain.useCaseEquation

import com.example.mycalculator.domain.EquationRepository


class AddCharUseCase(private val equationRepository: EquationRepository) {
    fun addChar(appendedChar: Char, cursorPosition: Int, textEquation: String) {
        equationRepository.addChar(appendedChar, cursorPosition, textEquation)
    }
}