package com.example.mycalculator.domain

import java.lang.RuntimeException
import java.util.*


class Equation(
    _equation: String = "",
    var isCorrectEquation: Boolean = false,
//   _answer: String = "",
    var id: Int = INIT_ID
) {

    companion object {
        const val WRONG_EQUATION = "NaN"
        const val INIT_ID = 0
    }
    var equation: String = ""
        set(value) {
            field = value
            isCorrectEquation = answer != WRONG_EQUATION

        }


    var answer: String = ""
        set(value) {
            field = calculate(equation)
            isCorrectEquation = field != WRONG_EQUATION

        }
        get() {
            return calculate(equation)
        }

    init {
//        answer = _answer
        equation = _equation
    }

    fun copy(): Equation{
        return Equation(equation,isCorrectEquation,id)
    }


    private fun calculate(expression: String): String {
        return try {
            val tokens = getTokens(expression)
            val postfix = toPostfix(tokens)
            val answer = evalPostfix(postfix).toString()
            deleteZeroFromEnd(answer)
        } catch (e: RuntimeException) {
            WRONG_EQUATION
        }

    }


    private fun getTokens(expression: String): List<String> {
        val result = ArrayList<String>()
        var number = ""
        for (i in expression.indices) {
            val c = expression[i]
            if (c == '-' && (i == 0 || expression[i - 1] in "+-x/%(")) {
                number += c
            } else if (c in '0'..'9' || c == '.') {
                number += c
                if (i == expression.lastIndex) {
                    result.add(number)
                }
            } else {
                if (number.isNotEmpty()) {
                    result.add(number)
                    number = ""
                }
                result.add(c.toString())
            }
        }
        return result
    }

    private fun toPostfix(tokens: List<String>): List<String> {
        val result = ArrayList<String>()
        val stack = Stack<String>()
        for (token in tokens) {
            when (token) {
                in "+-x/%" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(" && precedence(stack.peek()) >= precedence(token)) {
                        result.add(stack.pop())
                    }
                    stack.push(token)
                }

                "(" -> stack.push(token)
                ")" -> {
                    while (stack.peek() != "(") {
                        result.add(stack.pop())
                    }
                    stack.pop()
                }

                else -> result.add(token)
            }
        }
        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }
        return result
    }

    private fun evalPostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()
        for (token in postfix) {
            if (token in "+-x/%") {
                val y = stack.pop()
                val x = stack.pop()
                stack.push(eval(x, y, token))
            } else {
                stack.push(token.toDouble())
            }
        }
        return stack.pop()
    }

    private fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "x", "/", "%" -> 2
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    private fun eval(x: Double, y: Double, op: String): Double {
        return when (op) {
            "+" -> x + y
            "-" -> x - y
            "x" -> x * y
            "/" -> x / y
            else -> throw IllegalArgumentException("Invalid operator")
        }
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
