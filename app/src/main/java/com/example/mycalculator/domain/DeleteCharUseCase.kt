package com.example.mycalculator.domain


class DeleteCharUseCase(private val equationRepository: EquationRepository) {
    fun deleteChar(cursorPosition: Int) {
        equationRepository.deleteChar(cursorPosition)
    }
}