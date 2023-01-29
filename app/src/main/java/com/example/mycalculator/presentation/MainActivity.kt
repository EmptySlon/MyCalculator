package com.example.mycalculator.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var equationViewModel: EquationViewModel
    private lateinit var eqListViewModel: EquationListViewModel

    private lateinit var equationListAdapter: EquationRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txCalculation.showSoftInputOnFocus = false
        equationViewModel = ViewModelProvider(this)[EquationViewModel::class.java]
        eqListViewModel = ViewModelProvider(this)[EquationListViewModel::class.java]

        binding.equationViewModel = equationViewModel


        setupRecycleView()

        eqListViewModel.equationList.observe(this) {
            equationListAdapter.equationList = it
        }




//        binding.btDelete.setOnClickListener {
//            deleteCharToEquation()
//        }
//        binding.txCalculation.setOnClickListener {
//            equationViewModel.enableVisibilityOfCursor()
//        }
        binding.btEquals.setOnClickListener {
            val textEquation = binding.txCalculation.text.toString()
            equationViewModel.calculateResult(textEquation)
            val equation = equationViewModel.equation.value
            if (equation != null) {
                eqListViewModel.addEquationList(equation)
                binding.listCalculation.smoothScrollToPosition(equationListAdapter.itemCount - 1)
            }
        }




        equationViewModel.equationAnswer.observe(this) {
            if (!it.isNullOrBlank()) binding.txAnswer.visibility = View.VISIBLE
            else binding.txAnswer.visibility = View.GONE
            binding.txAnswer.text = it
        }
//        TODO ошибка при переносе equationText в xml
        equationViewModel.equationText.observe(this) {
            binding.txCalculation.setText(it)
        }

        equationViewModel.visibleCursor.observe(this) {
            binding.txCalculation.isCursorVisible = it
        }

        equationViewModel.cursorPosition.observe(this) {
            binding.txCalculation.setSelection(it)
        }

        equationListAdapter.onEquationClickListener = {
            equationViewModel.setEquation(it)
        }


    }


    private fun setupRecycleView() {
        val rvEquation = binding.listCalculation
        rvEquation.layoutManager = LinearLayoutManager(this)
        equationListAdapter = EquationRecyclerAdapter()
        rvEquation.adapter = equationListAdapter
    }

//    private fun deleteCharToEquation() {
//        val cursorPosition = binding.txCalculation.selectionEnd
//        equationViewModel.deleteChar(cursorPosition)
//    }



}


