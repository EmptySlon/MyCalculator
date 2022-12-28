package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

class GetEquationListUseCase (private val equationListRepository: EquationListRepository) {
    fun getEquationList(): LiveData<List<Equation>> {
        return equationListRepository.getEquationList()
    }
}