package com.example.mycalculator.domain



class SetEquationUseCase(private val equationRepository: EquationRepository)  {
    fun setEquation(newEquation: Equation){
        equationRepository.setEquation(newEquation)
    }
}
