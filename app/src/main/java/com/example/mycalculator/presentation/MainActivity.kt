package com.example.mycalculator.presentation

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mycalculator.databinding.ActivityMainBinding
import com.example.mycalculator.domain.Equation


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var equationViewModel: EquationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txCalculation.showSoftInputOnFocus = false
        equationViewModel = ViewModelProvider(this)[EquationViewModel::class.java]

        with(binding)
        {
            bt0.setOnClickListener { addCharToEquation(it) }
            bt1.setOnClickListener { addCharToEquation(it) }
            bt2.setOnClickListener { addCharToEquation(it) }
            bt3.setOnClickListener { addCharToEquation(it) }
            bt4.setOnClickListener { addCharToEquation(it) }
            bt5.setOnClickListener { addCharToEquation(it) }
            bt6.setOnClickListener { addCharToEquation(it) }
            bt7.setOnClickListener { addCharToEquation(it) }
            bt8.setOnClickListener { addCharToEquation(it) }
            bt9.setOnClickListener { addCharToEquation(it) }
            btComma.setOnClickListener { addCharToEquation(it) }
            btAdd.setOnClickListener { addCharToEquation(it) }
            btDivision.setOnClickListener { addCharToEquation(it) }
            btMultiplication.setOnClickListener { addCharToEquation(it) }
            btSubtraction.setOnClickListener { addCharToEquation(it) }
            btLeftBracket.setOnClickListener { addCharToEquation(it) }
            btRightBracket.setOnClickListener { addCharToEquation(it) }
            btDelete.setOnLongClickListener {
                equationViewModel.deleteEquation()
                txAnswer.visibility = View.GONE
                true
            }
            btDelete.setOnClickListener {
                deleteCharToEquation()
            }
            txCalculation.setOnClickListener {
                txCalculation.isCursorVisible = true
            }


        }

        equationViewModel.equation.observe(this) {
            updateTextView(it)
        }


    }

    private fun deleteCharToEquation() {
        val cursorPosition = binding.txCalculation.selectionEnd
        equationViewModel.deleteChar(cursorPosition)
        binding.txCalculation.setSelection(cursorPosition - 1)
    }

    private fun updateTextView(equation: Equation) {
        with(binding) {
            val cursorPosition = txCalculation.selectionEnd
            val equationText = equation.equation
            txCalculation.setText(equationText)
            txCalculation.isCursorVisible = cursorPosition + 1 != equationText.length
            if (txAnswer.visibility != View.VISIBLE) txAnswer.visibility = View.VISIBLE
            txAnswer.text = equationViewModel.equation.value?.answer
        }
    }

    private fun addCharToEquation(button: View) {
        button as Button
        val appendedChar = button.text.first()
        val cursorPosition = binding.txCalculation.selectionEnd
        equationViewModel.addChar(appendedChar, cursorPosition)
        binding.txCalculation.setSelection(cursorPosition + 1)
    }
}


