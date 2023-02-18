package com.example.mycalculator.domain.useCaseEquation

import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationRepository


class SetEquationUseCase(private val equationRepository: EquationRepository)  {
    fun setEquation(newEquation: Equation){
        equationRepository.setEquation(newEquation)
    }
}
