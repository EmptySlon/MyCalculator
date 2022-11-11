package com.example.mycalculator.domain


class AddCharUseCase(private val equationRepository: EquationRepository) {
    fun addChar(appendedChar: Char, cursorPosition: Int) {
        equationRepository.addChar(appendedChar, cursorPosition)
    }
}