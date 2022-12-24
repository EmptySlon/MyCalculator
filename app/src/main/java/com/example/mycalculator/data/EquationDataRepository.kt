package com.example.mycalculator.data

import com.example.mycalculator.domain.Equation

object EquationDataRepository {
    private var listEquation = mutableListOf<Equation>()

    fun getListEquationRepository(): MutableList<Equation> {
        return listEquation
    }

    fun addEquationToRepository(equation: Equation) {
        listEquation.add(equation)
    }



}