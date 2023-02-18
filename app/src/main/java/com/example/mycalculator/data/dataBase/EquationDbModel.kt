package com.example.mycalculator.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "equation_item")
class EquationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val equation: String,
    val isCorrectEquation: Boolean,
    var answer: String
    )