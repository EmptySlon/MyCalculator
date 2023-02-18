package com.example.mycalculator.domain.useCaseDB

import androidx.lifecycle.LiveData
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationListRepository

class GetEquationListUseCase (private val equationListRepository: EquationListRepository) {
    fun getEquationList(): LiveData<List<Equation>> {
        return equationListRepository.getEquationList()
    }
}