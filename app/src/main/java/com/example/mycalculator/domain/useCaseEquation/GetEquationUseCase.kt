package com.example.mycalculator.domain.useCaseEquation

import androidx.lifecycle.LiveData
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationRepository

class GetEquationUseCase(private val equationRepository: EquationRepository) {
    fun getEquation(): LiveData<Equation> {
        return equationRepository.getEquation()
    }
}