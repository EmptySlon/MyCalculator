package com.example.mycalculator.presentation

import androidx.lifecycle.ViewModel
import com.example.mycalculator.data.EquationRepositoryImpl
import com.example.mycalculator.domain.*

class EquationViewModel: ViewModel() {

    private val repository = EquationRepositoryImpl

    private val addCharUseCase = AddCharUseCase(repository)
    private val calculateResultUseCase = CalculateResultUseCase(repository)
    private val deleteCharUseCase = DeleteCharUseCase(repository)
    private val getEquationUseCase = GetEquationUseCase(repository)
    private val deleteEquationUseCase = DeleteEquationUseCase(repository)

    val equation = getEquationUseCase.getEquation()

    fun addChar(appendedChar: Char, cursorPosition: Int){
        addCharUseCase.addChar(appendedChar, cursorPosition)
    }

    fun deleteChar(cursorPosition: Int){
        deleteCharUseCase.deleteChar(cursorPosition)
    }

    fun calculateResult(){
        calculateResultUseCase.calculateResult()
    }

    fun deleteEquation() {
        deleteEquationUseCase.deleteEquation()
    }



}