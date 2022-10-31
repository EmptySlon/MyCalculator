package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding
import com.example.mycalculator.model.Equation
import com.example.mycalculator.model.CustomEditText
import java.lang.Math.abs


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.txCalculation.showSoftInputOnFocus = false;
        var textEd = binding.txCalculation.text.toString()
        val equation = Equation(textEd)
        var startCursorPosition = binding.txCalculation.selectionStart
        var cursorPosition = binding.txCalculation.selectionEnd
        binding.txCalculation.setOnClickListener {
            it as EditText
            it.isCursorVisible = true
        }
        fun deleteSpace(equation: String): String = equation.filter { it != ' ' }


        fun addSpaceToString(equation: String): String {
            val listOldSubstring =
                equation
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
            var newEquation = equation
            for ((index, oldString) in listOldSubstring.withIndex()) {
                newEquation = newEquation.replace(oldString, listNewSubstring[index])
            }
            return newEquation
        }

        fun updateTxAnswer(checkCorrectEquation: Boolean = false) {
            textEd = deleteSpace(binding.txCalculation.text.toString())
            equation.equation = textEd
            var answer = equation.calculateAnswer()
            if (checkCorrectEquation && !equation.isCorrectEquation) {
                Toast.makeText(this@MainActivity, "Не правильные скобки", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.txAnswer.text = addSpaceToString(answer)
        }

        fun addChar(button: View) {

            button as Button
            val appendedChar = button.text.toString()
//
//            startCursorPosition = binding.txCalculation.selectionStart
//            cursorPosition = binding.txCalculation.selectionEnd
//
//            if (binding.txCalculation.isFocused) {
//
//                val lastChar =
//                    when {
//                        textEd.isBlank() -> ""
//                        cursorPosition == 0 -> ""
//                        else -> textEd[cursorPosition - 1].toString()
//                    }
//                when {
//                    Regex("\\)").matches(lastChar) -> if (Regex("[\\d]").matches(appendedChar)) return
//                    Regex("\\(").matches(appendedChar) -> if (Regex("[\\d]").matches(lastChar)) return
//                    Regex("[\\D]").matches(appendedChar) && appendedChar != "-" -> {
//                        if (Regex("[\\D]").matches(lastChar)) return
//                    }
//                }
//                textEd =  textEd.removeRange(
//                        cursorPosition,
//                        textEd.lastIndex + 1
//                    ) + appendedChar + textEd.drop(cursorPosition)
//                if (cursorPosition + 1 == textEd.length) {
//                    binding.txCalculation.setSelection((textEd).length)
//                    binding.txCalculation.isCursorVisible = false
//                    binding.txCalculation.clearFocus()
//                } else {
//                    binding.txCalculation.setSelection(cursorPosition + 1)
//                }
//            } else {
//                var positionDifference =
//                    binding.txCalculation.text!!
//                        .let { text -> text.dropLast(text.toString().length - cursorPosition + 1) }
//                        .count { it == ' ' }
//                val lastChar =
//                    when {
//                        textEd.isBlank() -> ""
//                        cursorPosition == 0 -> ""
//                        else -> textEd[cursorPosition - 1 - positionDifference].toString()
//                    }
//                when {
//                    Regex("\\)").matches(lastChar) -> if (Regex("[\\d]").matches(appendedChar)) return
//                    Regex("\\(").matches(appendedChar) -> if (Regex("[\\d]").matches(lastChar)) return
//                    Regex("[\\D]").matches(appendedChar) && appendedChar != "-" -> {
//                        if (Regex("[\\D]").matches(lastChar)) return
//                    }
//                }
//                textEd =
//                    textEd.removeRange(
//                        cursorPosition - positionDifference,
//                        textEd.lastIndex + 1
//                    ) + appendedChar + textEd.drop(cursorPosition - positionDifference)
//                binding.txCalculation.setText(addSpaceToString(textEd))
//                if (cursorPosition - positionDifference + 1 == textEd.length) {
//                    binding.txCalculation.setSelection(addSpaceToString(textEd).length)
//                    binding.txCalculation.isCursorVisible = false
//                    binding.txCalculation.clearFocus()
//                } else binding.txCalculation.setSelection(cursorPosition + 1)
//            }


            startCursorPosition = binding.txCalculation.selectionStart
            cursorPosition = binding.txCalculation.selectionEnd
            var positionDifference =
                binding.txCalculation.text!!
                    .let { text -> text.dropLast(text.toString().length - cursorPosition + 1) }
                    .count { it == ' ' }
            Log.d("MyTag", "cursorPosition = $cursorPosition")
            Log.d("MyTag", "positionDifference = $positionDifference")
//            Log.d("MyTag", "textEd = $textEd")
//            textEd = addSpaceToString(textEd)
            val lastChar =
                when {
                    textEd.isBlank() -> ""
                    cursorPosition == 0 -> ""
                    else -> textEd[cursorPosition - 1 - positionDifference].toString()
                }
//            binding.txCalculation.isCursorVisible = cursorPosition != textEd.length

            when {
                Regex("\\)").matches(lastChar) -> {
                    if (Regex("[\\d]").matches(appendedChar)) return
                }
                Regex("\\(").matches(appendedChar) -> {
                    if (Regex("[\\d]").matches(lastChar)) return

                }

                Regex("[\\D]").matches(appendedChar) && appendedChar != "-" -> {
                    if (Regex("[\\D]").matches(lastChar)) return
                }
            }

//            if (startCursorPosition == -1) {
////                textEd = deleteSpace(textEd)
//                textEd = textEd + appendedChar
//                binding.txCalculation.setText(addSpaceToString(textEd))
//            } else {
//                textEd = addSpaceToString(textEd)
                textEd =
                    textEd.removeRange(
                        cursorPosition - positionDifference,
                        textEd.lastIndex + 1
                    ) + appendedChar + textEd.drop(cursorPosition - positionDifference)
//            }
//                textEd = deleteSpace(textEd)
//                positionDifference =
//                    addSpaceToString(textEd)
//                        .let { text -> text.dropLast(text.toString().length - cursorPosition + 1) }
//                        .count { it == ' ' }
//                if (binding.txCalculation.isFocused) binding.txCalculation.setText(textEd)
//                else binding.txCalculation.setText(addSpaceToString(textEd))
//                binding.txCalculation.setSelection(addSpaceToString(textEd).length )
            if (cursorPosition - positionDifference + 1 == textEd.length) {
                binding.txCalculation.setText(addSpaceToString(textEd))
                binding.txCalculation.setSelection(addSpaceToString(textEd).length)
                binding.txCalculation.isCursorVisible = false
                binding.txCalculation.clearFocus()

//                    if (binding.txCalculation.isFocused) binding.txCalculation.setSelection((textEd).length)
//                    else binding.txCalculation.setSelection(addSpaceToString(textEd).length)
////                    binding.txCalculation.setSelection(addSpaceToString(textEd).length )
//                    binding.txCalculation.isCursorVisible = false
//                    binding.txCalculation.clearFocus()
            } else {
                binding.txCalculation.setText(textEd)
                binding.txCalculation.setSelection(cursorPosition + 1)
            }


            updateTxAnswer()
        }


        with(binding)
        {
            bt0.setOnClickListener { addChar(it) }
            bt1.setOnClickListener { addChar(it) }
            bt2.setOnClickListener { addChar(it) }
            bt3.setOnClickListener { addChar(it) }
            bt4.setOnClickListener { addChar(it) }
            bt5.setOnClickListener { addChar(it) }
            bt6.setOnClickListener { addChar(it) }
            bt7.setOnClickListener { addChar(it) }
            bt8.setOnClickListener { addChar(it) }
            bt9.setOnClickListener { addChar(it) }
            btComma.setOnClickListener { addChar(it) }
            btAdd.setOnClickListener { addChar(it) }
            btDivision.setOnClickListener { addChar(it) }
            btMultiplication.setOnClickListener { addChar(it) }
            btSubtraction.setOnClickListener { addChar(it) }
            btEquals.setOnClickListener {
//                equation.equation = textEd
                updateTxAnswer(true)
                binding.txCalculation.setText(addSpaceToString(textEd))
                binding.txCalculation.setSelection(addSpaceToString(textEd).length)
                binding.txCalculation.isCursorVisible = false
                binding.txCalculation.clearFocus()
//                val answer = equation.calculateAnswer()
//                if (!equation.isCorrectEquation) {
//                    Toast.makeText(this@MainActivity, "Не правильные скобки", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//                binding.txAnswer.text = answer
            }
            btLeftBracket.setOnClickListener { addChar(it) }
            btRightBracket.setOnClickListener { addChar(it) }
            // TODO: перепесать с условием isFocused
            btDelete.setOnClickListener {
                startCursorPosition = binding.txCalculation.selectionStart
                cursorPosition = binding.txCalculation.selectionEnd
                var positionDifference =
                    binding.txCalculation.text!!
                        .let { text -> text.dropLast(text.toString().length - cursorPosition + 1) }
                        .count { it == ' ' }
//                cursorPosition -= addSpaceToString(textEd).length - textEd.length
                cursorPosition -= positionDifference
                Log.d("MyTag", cursorPosition.toString())
//                Log.v("MyTag", "startCursorPosition = $startCursorPosition")
//                Log.v("MyTag", "cursorPosition = $cursorPosition")
                if (startCursorPosition == 0) return@setOnClickListener
                textEd =
                    if (startCursorPosition == -1) textEd.dropLast(1)
                    else textEd.removeRange(cursorPosition - 1, cursorPosition)
                if (binding.txCalculation.isFocused) binding.txCalculation.setText(textEd)
                else binding.txCalculation.setText(addSpaceToString(textEd))
//                binding.txCalculation.setText(addSpaceToString(textEd))
                if (textEd.length > 3) {
                    positionDifference =
                        binding.txCalculation.text!!
                            .let { text -> text.dropLast(textEd.length - cursorPosition + positionDifference) }
                            .count { it == ' ' }
                }

                txCalculation.setSelection(cursorPosition - 1 + positionDifference)
                updateTxAnswer()
            }
            btDelete.setOnLongClickListener {
                textEd = ""
                binding.txCalculation.setText(textEd)
                binding.txAnswer.text = textEd
                true
            }
            txCalculation.setOnFocusChangeListener { v, hasFocus ->
                v as EditText
                if (hasFocus) {

                    cursorPosition = v.selectionEnd
                    v.isCursorVisible = true
                    v.setText(textEd)
                    v.setSelection(textEd.length)
                } else {
                    v.isCursorVisible = false
                }
            }

//            txCalculation.setOnClickListener {
//                it as EditText
//                cursorPosition = it.selectionEnd
//                it.setText(textEd)
//                it.setSelection(textEd.length)
//            }


        }


    }


}