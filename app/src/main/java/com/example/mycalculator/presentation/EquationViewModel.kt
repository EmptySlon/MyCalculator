package com.example.mycalculator.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    private  var  _equationAnswer = MutableLiveData<String>()
    val equationAnswer: LiveData<String>
    get() = _equationAnswer

    private  var  _equationText = MutableLiveData<String>()
    val equationText: LiveData<String>
        get() = _equationText

    private  var  _cursorPosition = MutableLiveData<Int>(0)
    val cursorPosition: LiveData<Int>
        get() = _cursorPosition

    private  var  _visibleCursor = MutableLiveData<Boolean>()
    val visibleCursor: LiveData<Boolean>
        get() = _visibleCursor



    fun addChar(appendedChar: Char, cursorPosition: Int){
        addCharUseCase.addChar(appendedChar, cursorPosition)
        updateEquationValue()
        _cursorPosition.value = cursorPosition + 1
        updateCursorVisibility()
    }




    fun deleteChar(cursorPosition: Int){
        deleteCharUseCase.deleteChar(cursorPosition)

        updateEquationValue()
        if (cursorPosition > 0 )  _cursorPosition.value = cursorPosition - 1
        if (cursorPosition == 0 ) _visibleCursor.value = false
        updateCursorVisibility()

    }

    fun calculateResult(){
        calculateResultUseCase.calculateResult()
    }

    fun deleteEquation() {
        deleteEquationUseCase.deleteEquation()
        updateEquationValue()
        _cursorPosition.value = 0
        updateCursorVisibility()
    }

    fun enableVisibilityOfCursor(){
        _visibleCursor.value = true
    }

    private fun updateEquationValue() {
        _equationAnswer.value = equation.value?.answer
        _equationText.value = equation.value?.equation
    }

    private fun updateCursorVisibility() {
        if ((equation.value?.equation?.length ?: 0) == (_cursorPosition.value ?: 0)) {
            _visibleCursor.value = false
        }
    }



}