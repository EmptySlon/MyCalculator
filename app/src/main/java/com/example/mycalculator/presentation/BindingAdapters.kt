package com.example.mycalculator.presentation

import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mycalculator.R

@BindingAdapter("txAnswer")
fun bindingTxAnswer(textView: TextView, answer: String) {
    textView.text = textView.context.getString(R.string.equal_in_rv, answer)
}

@BindingAdapter("addCharOnClickListener", "idTextCalculation")
fun bindingAddCharOnClickListener(
    button: Button,
    equationViewModel: EquationViewModel,
    txCalculation: EditText,
) {
    button.setOnClickListener {
        val appendedChar = button.text.first()
        val cursorPosition = txCalculation.selectionEnd
        val textEquation = txCalculation.text.toString()
        equationViewModel.addChar(appendedChar, cursorPosition, textEquation)
    }
}

@BindingAdapter("setOnLongClickListenerToDelete")
fun bindingSetOnLongClickListener(button: ImageButton, equationViewModel: EquationViewModel) {
    button.setOnLongClickListener {
        equationViewModel.deleteEquation()
        true
    }
}

@BindingAdapter("setOnClickListenerToDelete", "idTextCalculation")
fun bindingSetOnClickListener(
    button: ImageButton, equationViewModel: EquationViewModel, txCalculation: EditText,
) {
    button.setOnClickListener {
        val cursorPosition = txCalculation.selectionEnd
        equationViewModel.deleteChar(cursorPosition)

    }
}

@BindingAdapter("textEquation")
fun bindingTextEquation(txCalculation: EditText, textEquation: String ) {
    txCalculation.setText(textEquation)
}