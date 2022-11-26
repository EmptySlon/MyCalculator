package com.example.mycalculator.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mycalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
                true
            }
            btDelete.setOnClickListener {
                deleteCharToEquation()
            }
            txCalculation.setOnClickListener {
                txCalculation.isCursorVisible = true
            }

        }

        equationViewModel.equationAnswer.observe(this){
            if (!it.isNullOrBlank()) binding.txAnswer.visibility = View.VISIBLE
            else binding.txAnswer.visibility = View.GONE
            binding.txAnswer.text = it
        }

        equationViewModel.equationText.observe(this){
            binding.txCalculation.setText(it)
        }

        equationViewModel.visibleCursor.observe(this){
             binding.txCalculation.isCursorVisible = it
        }

        equationViewModel.cursorPosition.observe(this) {
            binding.txCalculation.setSelection(it)
        }


    }

    private fun deleteCharToEquation() {
        val cursorPosition = binding.txCalculation.selectionEnd
        equationViewModel.deleteChar(cursorPosition)
    }


    private fun addCharToEquation(button: View) {
        button as Button
        val appendedChar = button.text.first()
        val cursorPosition = binding.txCalculation.selectionEnd
        equationViewModel.addChar(appendedChar, cursorPosition)
    }
}


