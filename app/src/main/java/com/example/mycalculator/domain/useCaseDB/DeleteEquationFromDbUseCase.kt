package com.example.mycalculator.domain.useCaseDB

import com.example.mycalculator.domain.EquationListRepository

class DeleteEquationFromDbUseCase(private val equationListRepository: EquationListRepository) {
    suspend fun deleteEquationFromDb(equationId: Int) {
        equationListRepository.deleteEquationFromDb(equationId)
    }
}
