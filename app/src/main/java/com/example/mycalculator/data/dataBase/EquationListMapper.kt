package com.example.mycalculator.data.dataBase

import com.example.mycalculator.domain.Equation


class EquationListMapper {

    fun mapEntityToDbModel(equation: Equation): EquationDbModel = EquationDbModel(
        id = equation.id,
        equation = equation.equation,
        isCorrectEquation = equation.isCorrectEquation,
        answer = equation.answer
    )

    fun mapDbModelToEntity(equationDbModel: EquationDbModel): Equation = Equation(
        id = equationDbModel.id,
        _equation = equationDbModel.equation,
        isCorrectEquation = equationDbModel.isCorrectEquation,
        _answer = equationDbModel.answer
    )

    fun mapListDbModelToListEntity(list: List<EquationDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}