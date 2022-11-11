package com.example.mycalculator.domain


class DeleteCharUseCase(private val equationRepository: EquationRepository) {
    fun deleteChar(char: Char, cursorPosition: Int) {
        equationRepository.deleteChar(char, cursorPosition)
    }
}