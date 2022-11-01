package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils.split
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding.txCalculation.showSoftInputOnFocus = false
        var textEd = binding.txCalculation.text.toString()
        val equation = Equation(textEd)
        var startCursorPosition = binding.txCalculation.selectionStart
        var cursorPosition = binding.txCalculation.selectionEnd

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
            binding.txAnswer.visibility =
                if (answer.isNotBlank()) TextView.VISIBLE
                else TextView.GONE

            binding.txAnswer.text = addSpaceToString(answer)
        }

        fun addChar(button: View) {

            button as Button
            val appendedChar = button.text.toString()
            startCursorPosition = binding.txCalculation.selectionStart
            cursorPosition = binding.txCalculation.selectionEnd
            var positionDifference =
                binding.txCalculation.text!!
                    .let { text -> text.dropLast(text.toString().length - cursorPosition + 1) }
                    .count { it == ' ' }
//            Log.d("MyTag", "cursorPosition = $cursorPosition")
//            Log.d("MyTag", "positionDifference = $positionDifference")
            val lastChar =
                when {
                    textEd.isBlank() -> ""
                    cursorPosition == 0 -> ""
                    else -> textEd[cursorPosition - 1 - positionDifference].toString()
                }

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

            textEd =
                textEd.removeRange(
                    cursorPosition - positionDifference,
                    textEd.lastIndex + 1
                ) + appendedChar + textEd.drop(cursorPosition - positionDifference)

            if (cursorPosition - positionDifference + 1 == textEd.length) {
                binding.txCalculation.setText(addSpaceToString(textEd))
                binding.txCalculation.setSelection(addSpaceToString(textEd).length)
                binding.txCalculation.isCursorVisible = false
                binding.txCalculation.clearFocus()
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
            btLeftBracket.setOnClickListener { addChar(it) }
            btRightBracket.setOnClickListener { addChar(it) }

            btEquals.setOnClickListener {
                updateTxAnswer(true)
                binding.txCalculation.setText(addSpaceToString(textEd))
                binding.txCalculation.setSelection(addSpaceToString(textEd).length)
                binding.txCalculation.isCursorVisible = false
                binding.txCalculation.clearFocus()
            }

            btDelete.setOnClickListener {
                cursorPosition = binding.txCalculation.selectionEnd
                if (startCursorPosition == 0) return@setOnClickListener
                if (binding.txCalculation.isFocused) {
                    textEd = textEd.removeRange(cursorPosition - 1, cursorPosition)
                    binding.txCalculation.setText(textEd)
                    txCalculation.setSelection(cursorPosition - 1)
                } else {
                    textEd = textEd.dropLast(1)
                    binding.txCalculation.setText(addSpaceToString(textEd))
                    txCalculation.setSelection(addSpaceToString(textEd).length)
                }
                updateTxAnswer()
            }

            btDelete.setOnLongClickListener {
                textEd = ""
                binding.txCalculation.setText(textEd)
                binding.txAnswer.text = textEd
                binding.txAnswer.visibility =TextView.GONE
                true
            }
            txCalculation.setOnFocusChangeListener { v, hasFocus ->
                v as EditText
                if (hasFocus) {
// TODO: не правильно устанавливает setSelection
                    cursorPosition = v.selectionEnd
                    v.isCursorVisible = true
                    val positionDifference =
                        addSpaceToString(textEd)
                            .let { it.dropLast(it.length - cursorPosition) }
                            .count { it == ' ' }
//                    Log.v("MyTag", "cursorPosition = $cursorPosition")
//                    Log.v("MyTag", "positionDifference = $positionDifference")
                    v.setText(textEd)
                    val cursorPosition2 = v.selectionEnd
                    Log.v("MyTag", "cursorPositionSETTEXT = $cursorPosition2")
                } else {
                    v.isCursorVisible = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        binding.txCalculation.hideSoftInputFromWindow()

    }


}