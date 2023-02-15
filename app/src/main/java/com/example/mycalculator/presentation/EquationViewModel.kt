package com.example.mycalculator.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycalculator.data.EquationRepositoryImpl
import com.example.mycalculator.domain.*

class EquationViewModel : ViewModel() {

    private val repository = EquationRepositoryImpl

    private val addCharUseCase = AddCharUseCase(repository)
    private val calculateResultUseCase = CalculateResultUseCase(repository)
    private val deleteCharUseCase = DeleteCharUseCase(repository)
    private val getEquationUseCase = GetEquationUseCase(repository)
    private val deleteEquationUseCase = DeleteEquationUseCase(repository)
    private val setEquationUseCase = SetEquationUseCase(repository)

    val equation = getEquationUseCase.getEquation()


    private var _equationAnswer = MutableLiveData<String>(Equation.WRONG_EQUATION)
    val equationAnswer: LiveData<String>
        get() = _equationAnswer

    private var _equationText = MutableLiveData<String>()
    val equationText: LiveData<String>
        get() = _equationText

    private var _cursorPosition = MutableLiveData<Int>()
    val cursorPosition: LiveData<Int>
        get() = _cursorPosition

    private var _visibleCursor = MutableLiveData<Boolean>()
    val visibleCursor: LiveData<Boolean>
        get() = _visibleCursor

    fun setEquation(newEquation: Equation) {
        setEquationUseCase.setEquation(newEquation)
        updateEquationValue()
        _cursorPosition.value = newEquation.equation.length
        updateCursorVisibility()
    }

    fun addChar(appendedChar: Char, cursorPosition: Int, textEquation: String) {
        addCharUseCase.addChar(appendedChar, cursorPosition, textEquation)
        updateEquationValue()
        when {
            (_equationText.value?.length ?: 0) > textEquation.length -> {
                _cursorPosition.value = cursorPosition + 1
            }
            (_equationText.value?.length ?: 0) > (cursorPosition + 1) -> {
                _cursorPosition.value = cursorPosition
            }
            (_equationText.value?.length ?: 0) == textEquation.length -> {
                _cursorPosition.value = _equationText.value?.length
            }
        }
        updateCursorVisibility()
    }


    fun deleteChar(cursorPosition: Int) {
        deleteCharUseCase.deleteChar(cursorPosition)
        updateEquationValue()
        if (cursorPosition > 0) _cursorPosition.value = cursorPosition - 1
        if (cursorPosition == 0) _visibleCursor.value = false
        updateCursorVisibility()

    }

    fun calculateResult(textEquation: String) {
        calculateResultUseCase.calculateResult(textEquation)
        updateEquationValue()
        _cursorPosition.value = _equationText.value?.length
    }

    fun deleteEquation() {
        deleteEquationUseCase.deleteEquation()
        updateEquationValue()
        _cursorPosition.value = 0
        updateCursorVisibility()
    }

    fun enableVisibilityOfCursor() {
        _visibleCursor.value = true
    }

    private fun updateEquationValue() {
        _equationAnswer.value = equation.value?.answer
        _equationText.value = equation.value?.equation
    }

    private fun updateCursorVisibility() {
        _visibleCursor.value =
            (equation.value?.equation?.length ?: 0) != (_cursorPosition.value ?: 0)
    }



}