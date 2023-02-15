package com.example.mycalculator.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalculator.data.EquationListRepositoryImpl
import com.example.mycalculator.domain.AddEquationUseCase
import com.example.mycalculator.domain.DeleteEquationFromDbUseCase
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.GetEquationListUseCase
import kotlinx.coroutines.launch

class EquationListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EquationListRepositoryImpl(application)

    private val getEquationListUseCase = GetEquationListUseCase(repository)
    private val addEquationListUseCase = AddEquationUseCase(repository)
    private val deleteEquationFromDbUseCase = DeleteEquationFromDbUseCase(repository)

    val equationList = getEquationListUseCase.getEquationList()


    fun addEquationList(equation: Equation) {
        if (equationList.value?.lastOrNull()?.equation != equation.equation && equation.isCorrectEquation) {
            viewModelScope.launch { addEquationListUseCase.addEquation(equation) }
        }
    }

    fun deleteEquationFromDb(equationId: Int) {
        viewModelScope.launch {
            deleteEquationFromDbUseCase.deleteEquationFromDb(equationId)
        }
    }


}