package com.example.mycalculator.domain

class DeleteEquationFromDbUseCase(private val equationListRepository: EquationListRepository) {
    suspend fun deleteEquationFromDb(equationId: Int) {
        equationListRepository.deleteEquationFromDb(equationId)
    }
}
