package com.example.mycalculator.domain.useCaseEquation

import com.example.mycalculator.domain.EquationRepository

class DeleteEquationUseCase (private val equationRepository: EquationRepository) {
    fun deleteEquation() {
        equationRepository.deleteEquation()
    }
}
