package com.example.mycalculator.model

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Equation(var equation: String = "") {

    var answer: String = ""
    var isCorrectEquation = (equation.count { it == '(' } == equation.count { it == ')' })

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
            _str = _str.replace("$fistNumber/$lastNumber", "$calculateNumber")
        }
        while (_str.contains(Regex("\\dx\\d"))) {
            val indexSing = _str.indexOf("x")
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber = (BigDecimal(fistNumber) * BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("${fistNumber}x$lastNumber", "$calculateNumber")
        }
        _str = checkCorrectSigns(_str)
        while (_str.contains(Regex("\\d-\\d"))) {
            val indexSing = _str.drop(1).indexOf("-") + 1
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber = (BigDecimal(fistNumber) - BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("$fistNumber-$lastNumber", "$calculateNumber")
            _str = checkCorrectSigns(_str)
        }

        while (_str.contains(Regex("\\d\\+\\d"))) {
            val indexSing = _str.indexOf("+")
            val fistNumber = getFistNumber(_str.dropLast(_str.length - indexSing))
            val lastNumber = getLastNumber(_str.drop(indexSing + 1))
            var calculateNumber =( BigDecimal(fistNumber) + BigDecimal(lastNumber)).toString()
            calculateNumber = deleteZeroFromEnd(calculateNumber)
            _str = _str.replace("$fistNumber+$lastNumber", "$calculateNumber")
            _str = checkCorrectSigns(_str)
        }
        isCorrectEquation = (equation.count { it == '(' } == equation.count { it == ')' })
        _str.toDouble()
        answer = _str
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

    fun calculateAnswer(): String {
        var tempEquation = equation
        try {
            while (tempEquation.contains(Regex("\\("))) {
                var temporalEquation =
                    tempEquation
                        .dropLast(tempEquation.length - tempEquation.indexOf(')'))
                        .let { it.drop(it.lastIndexOf("(") + 1) }
                val answerEquation = simpleCalculate(temporalEquation)
                println("$temporalEquation = $answerEquation")
                tempEquation = tempEquation.replace("(${temporalEquation})", answerEquation)
            }
            answer = simpleCalculate(tempEquation)
            return answer
        } catch (e: Exception) {
            isCorrectEquation = false
            return "NaN"
        }
    }


}