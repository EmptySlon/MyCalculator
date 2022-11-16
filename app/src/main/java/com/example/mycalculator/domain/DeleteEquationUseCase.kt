package com.example.mycalculator.domain

class DeleteEquationUseCase (private val equationRepository: EquationRepository) {
    fun deleteEquation() {
        equationRepository.deleteEquation()
    }
}
