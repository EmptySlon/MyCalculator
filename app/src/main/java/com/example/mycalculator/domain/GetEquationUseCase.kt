package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

class GetEquationUseCase(private val equationRepository: EquationRepository) {
    fun getEquation(): LiveData<Equation> {
        return equationRepository.getEquation()
    }
}