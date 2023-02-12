package com.example.mycalculator.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationRepository

object EquationRepositoryImpl : EquationRepository {

    private val equationLD = MutableLiveData<Equation>()
    private var equation: Equation = Equation()

    override fun addChar(appendedChar: Char, cursorPosition: Int, textEquation: String) {
        var equationValue = textEquation
        equationValue =
            equationValue.removeRange(
                cursorPosition,
                equationValue.lastIndex + 1
            ) + appendedChar + equationValue.drop(cursorPosition)

        equationValue = checkForDuplicateChar(equationValue)

        equation.equation = equationValue
        updateEquationLD()

    }

    private fun checkForDuplicateChar(equationValue: String): String {
        var equationValue1 = equationValue
        equationValue1 = equationValue1.replace("xx", "x")
        equationValue1 = equationValue1.replace("x+", "x")
        equationValue1 = equationValue1.replace("-+", "-")
        equationValue1 = equationValue1.replace("+x", "+")
        equationValue1 = equationValue1.replace("++", "+")
        equationValue1 = equationValue1.replace("+--", "+-")
        equationValue1 = equationValue1.replace("---", "--")
        return equationValue1
    }

    override fun deleteChar(cursorPosition: Int) {
        var equationValue = equation.equation
        if (cursorPosition == 0) return
        equationValue = equationValue.removeRange(cursorPosition - 1, cursorPosition)
        equation.equation = equationValue
        updateEquationLD()
    }

    override fun getEquation(): LiveData<Equation> {
        return equationLD
    }

    override fun calculateResult(textEquation: String) {
        var equationValue = textEquation

        while (equationValue.count { it == '(' } != equationValue.count { it == ')' }) {
            equationValue =
                if (equationValue.count { it == '(' } < equationValue.count { it == ')' }) "($equationValue"
                else "$equationValue)"
        }
        equation.equation = equationValue

        updateEquationLD()
    }



    override fun deleteEquation() {
        equation = Equation("", false)
        updateEquationLD()
    }

    override fun setEquation(newEquation: Equation) {
        equation = newEquation.copy()
        updateEquationLD()
    }

    private fun updateEquationLD() {
        equationLD.value = equation.copy()
    }




}





