package com.example.mycalculator.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationRepository
import java.math.BigDecimal
import java.math.RoundingMode

object EquationRepositoryImpl : EquationRepository {

    private val equationLD = MutableLiveData<Equation>()
    private val equation: Equation = Equation("", false)

    override fun addChar(appendedChar: Char, cursorPosition: Int) {
        var equationValue = equation.equation
        val correctedPosition = getPositionDifference(equationValue, cursorPosition)
        val lastChar =
            when {
                equationValue.isBlank() -> ""
                correctedPosition == 0 -> ""
                else -> equationValue[correctedPosition - 1].toString()
            }
        when {
            Regex("\\)").matches(lastChar) -> {
                if (Regex("[\\d]").matches(appendedChar.toString())) return
            }
            Regex("\\(").matches(appendedChar.toString()) -> {
                if (Regex("[\\d]").matches(lastChar)) return
            }
            Regex("[\\D]").matches(appendedChar.toString()) && appendedChar.toString() != "-" -> {
                if (Regex("[\\D]").matches(lastChar)) return
            }
        }
        equationValue =
            equationValue.removeRange(
                correctedPosition,
                equationValue.lastIndex + 1
            ) + appendedChar + equationValue.drop(correctedPosition)
        equation.equation = equationValue
        updateEquationLD()

    }

    override fun deleteChar(char: Char, cursorPosition: Int) {
        var equationValue = equation.equation
        val correctedPosition = getPositionDifference(equationValue, cursorPosition)
        equationValue = equationValue.removeRange(correctedPosition - 1, correctedPosition)
        equation.equation = equationValue
        updateEquationLD()
    }

    override fun getEquation(): LiveData<Equation> {
        return equationLD
    }


    override fun calculateResult(equation: Equation) {
        val answer: String
        var tempEquation = equation.equation
        equation.isCorrectEquation = (tempEquation.count { it == '(' } == tempEquation.count { it == ')' })

        try {
            while (tempEquation.contains(Regex("\\("))) {
                val temporalEquation =
                    tempEquation
                        .dropLast(tempEquation.length - tempEquation.indexOf(')'))
                        .let { it.drop(it.lastIndexOf("(") + 1) }
                val answerEquation = simpleCalculate(temporalEquation)
                println("$temporalEquation = $answerEquation")
                tempEquation = tempEquation.replace("(${temporalEquation})", answerEquation)
            }
            answer = simpleCalculate(tempEquation)
            equation.answer = answer
            updateEquationLD()
        } catch (e: Exception) {
            equation.isCorrectEquation = false
            equation.answer = "NaN"
            updateEquationLD()
        }

    }


    private fun updateEquationLD() {
        equationLD.value = equation.copy()
    }


    private fun addSpaceToString(equationValue: String): String {
        val listOldSubstring =
            equationValue
                .split(Regex("[()x+/-]"))
                .map {
                    val filteredString =
                        if (it.contains('.')) it.dropLast(it.length - it.indexOf("."))
                        else it
                    if (filteredString.length > 3) filteredString else ""
                }
                .filter { it.isNotBlank() }

        val listNewSubstring = listOldSubstring.map { text ->
            text
                .reversed()
                .chunked(3)
                .map { it.reversed() }
                .reversed()
                .joinToString(" ")
                .trim()
        }
        var newEquation = equationValue
        for ((index, oldString) in listOldSubstring.withIndex()) {
            newEquation = newEquation.replace(oldString, listNewSubstring[index])
        }
        return newEquation
    }

    private fun getPositionDifference(equationValue: String, position: Int): Int {
        val positionDifference =
            equationValue
                .let { text -> text.dropLast(text.length - position + 1) }
                .count { it == ' ' }
        return position - positionDifference
    }

    private fun simpleCalculate(str: String): String {
        var _str = str
        fun checkCorrectSigns(str: String): String {
            var newStr = str
            while (newStr.contains(Regex("--"))) {
                newStr = newStr.replace("--", "+")
            }
            while (newStr.contains(Regex("\\+-"))) {
                newStr = newStr.replace("+-", "-")
            }
            return newStr
        }

        fun getLastNumber(strLast: String): String {
            var listNumber = mutableListOf("")
            for (chr in strLast) {
                if (chr.isDigit() || chr == '.') listNumber[listNumber.lastIndex] =
                    listNumber.last() + chr
                else listNumber.add(chr.toString()).also { listNumber.add("") }
            }
            listNumber = listNumber.filterNot { it.isBlank() }.toMutableList()
            return when {
                listNumber.size == 1 -> listNumber.first()
                listNumber.first() == "-" -> listNumber[0] + listNumber[1]
                else -> listNumber.first()
            }
        }

        fun getFistNumber(strFirst: String): String {
            var listNumber = mutableListOf("")
            for (chr in strFirst) {
                if (chr.isDigit() || chr == '.') listNumber[listNumber.lastIndex] =
                    listNumber.last() + chr
                else listNumber.add(chr.toString()).also { listNumber.add("") }
            }
            listNumber = listNumber.filterNot { it.isBlank() }.toMutableList()
            return when {
                listNumber.size == 1 -> listNumber.last()
                listNumber.size == 2 && listNumber.first() == "-" -> listNumber.joinToString("")
                listNumber.reversed()[2].contains(Regex("\\D"))
                        && listNumber.reversed()[1] == "-" -> listNumber.reversed()[1] + listNumber.last()
                else -> listNumber.last()
            }
        }


        while (_str.contains(Regex("\\d\\/.?\\d"))) {
            val indexSing = _str.indexOf("/")
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber =
                BigDecimal(fistNumber).divide(BigDecimal(lastNumber), 8, RoundingMode.DOWN)
                    .toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("$fistNumber/$lastNumber", calculateNumber)
        }
        while (_str.contains(Regex("\\dx\\d"))) {
            val indexSing = _str.indexOf("x")
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber = (BigDecimal(fistNumber) * BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("${fistNumber}x$lastNumber", calculateNumber)
        }
        _str = checkCorrectSigns(_str)
        while (_str.contains(Regex("\\d-\\d"))) {
            val indexSing = _str.drop(1).indexOf("-") + 1
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber = (BigDecimal(fistNumber) - BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("$fistNumber-$lastNumber", calculateNumber)
            _str = checkCorrectSigns(_str)
        }

        while (_str.contains(Regex("\\d\\+\\d"))) {
            val indexSing = _str.indexOf("+")
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber = (BigDecimal(fistNumber) + BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("$fistNumber+$lastNumber", calculateNumber)
            _str = checkCorrectSigns(_str)
        }
        equation.isCorrectEquation = (equation.equation.count { it == '(' } == equation.equation.count { it == ')' })
        _str.toDouble()
//        equation.answer = _str
        return _str
    }

    private fun deleteZeroFromEnd(str: String): String {
        var count = 0
        if (str == "0" || !str.contains('.')) return str
        for (chr in str.reversed()) {
            if (chr == '0') ++count
            else break
        }
        return if (str.dropLast(count).last() == '.') str.dropLast(++count) else str.dropLast(count)
    }

}





