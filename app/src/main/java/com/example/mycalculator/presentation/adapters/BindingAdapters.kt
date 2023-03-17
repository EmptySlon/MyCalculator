package com.example.mycalculator.presentation.adapters

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculator.R
import com.example.mycalculator.domain.SettingApp
import com.example.mycalculator.presentation.viewModel.EquationListViewModel
import com.example.mycalculator.presentation.viewModel.EquationViewModel
import com.example.mycalculator.presentation.viewModel.SettingViewModel

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

@BindingAdapter("equationViewModel", "eqListViewModel", "idTextCalculation", "idListCalculation")
fun bindingAddEquationList(
    button: Button,
    equationViewModel: EquationViewModel,
    eqListViewModel: EquationListViewModel,
    txCalculation: EditText,
    listCalculation: RecyclerView,
) {
    button.setOnClickListener {
        val equationListAdapter = listCalculation.adapter
            ?: throw RuntimeException("equationListAdapter == null")
        val textEquation = txCalculation.text.toString()
        equationViewModel.calculateResult(textEquation)
        val equation = equationViewModel.equation.value
        if (equation != null) {
            eqListViewModel.addEquationList(equation)
            listCalculation.smoothScrollToPosition(equationListAdapter.itemCount)
        }
    }
}

@BindingAdapter("equationAnswer", "settingApp")
fun bindingEquationAnswer(txAnswer: TextView, equationAnswer: String?, settingApp: SettingApp?) {

    if (!equationAnswer.isNullOrBlank()) txAnswer.visibility = View.VISIBLE
    else txAnswer.visibility = View.GONE

    if (equationAnswer == null) txAnswer.text = equationAnswer
    else {
        val formattedType = "%." + (settingApp?.NumberAfterComma ?: 3) + "f"
        var formattedEquation = String.format(formattedType, equationAnswer.toFloat())
        while (formattedEquation.last() == '0'){
            formattedEquation = formattedEquation.dropLast(1)
        }
        if (formattedEquation.last() == ',') formattedEquation = formattedEquation.dropLast(1)
        txAnswer.setText(formattedEquation)
    }
}

@BindingAdapter("setOnEquationClickListener")
fun bindOnEquationClickListener(
    listCalculation: RecyclerView,
    equationViewModel: EquationViewModel
) {
    val adapter = listCalculation.adapter as EquationRecyclerAdapter
    adapter.onEquationClickListener = {
        equationViewModel.setEquation(it)
    }
}

@BindingAdapter("setOnListCalculationLongClickListener")
fun bindOnListCalculationLongClickListener(
    listCalculation: RecyclerView,
    eqListViewModel: EquationListViewModel
) {
    val adapter = listCalculation.adapter as EquationRecyclerAdapter
    adapter.onEquationLongClickListener = {
        eqListViewModel.deleteEquationFromDb(it)
    }
}



