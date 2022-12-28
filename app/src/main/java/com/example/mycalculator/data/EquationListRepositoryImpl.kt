package com.example.mycalculator.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationListRepository

object EquationListRepositoryImpl : EquationListRepository {

    private val equationListLD = MutableLiveData<List<Equation>>()
    private val equationList = mutableListOf<Equation>()

    override fun getEquationList(): LiveData<List<Equation>> {
        return equationListLD
    }

    override fun addEquation(equation: Equation) {

        if (equationList.isEmpty() || (equationList.last() != equation) && equation.isCorrectEquation) {
            equationList.add(equation)
            updateList()
        }
    }

    private fun updateList() {
        equationListLD.value = equationList.toList()
    }


}