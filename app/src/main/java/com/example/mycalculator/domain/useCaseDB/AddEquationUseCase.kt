package com.example.mycalculator.domain.useCaseDB

import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationListRepository

class AddEquationUseCase (private val equationListRepository: EquationListRepository) {
   suspend fun addEquation(equation: Equation) {
        equationListRepository.addEquation(equation)
    }
}