package com.example.mycalculator.presentation

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycalculator.data.preferences.PreferenceProvider
import com.example.mycalculator.databinding.ActivityMainBinding
import com.example.mycalculator.presentation.adapters.EquationRecyclerAdapter
import com.example.mycalculator.presentation.viewModel.EquationListViewModel
import com.example.mycalculator.presentation.viewModel.EquationViewModel
import com.example.mycalculator.presentation.viewModel.SettingViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var equationListAdapter: EquationRecyclerAdapter

    private val equationViewModel: EquationViewModel by lazy {
        ViewModelProvider(this)[EquationViewModel::class.java]
    }
    private val eqListViewModel: EquationListViewModel by lazy {
        ViewModelProvider(this)[EquationListViewModel::class.java]
    }
    private val settingViewModel: SettingViewModel by lazy {
        ViewModelProvider(this)[SettingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txCalculation.showSoftInputOnFocus = false


        setDataToBinding()
        setupRecycleView()

        eqListViewModel.equationList.observe(this) {
            equationListAdapter.equationList = it
        }
        settingViewModel.settingApp.observe(this){
            equationListAdapter.settingApp = it
            binding.settingApp = it


        }
        settingViewModel.changeNumberAfterComma()



    }

    private fun setDataToBinding() {
        binding.equationViewModel = equationViewModel
        binding.eqListViewModel = eqListViewModel
        binding.lifecycleOwner = this
    }

    private fun setupRecycleView() {
        val rvEquation = binding.listCalculation
        rvEquation.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        equationListAdapter = EquationRecyclerAdapter()
        rvEquation.adapter = equationListAdapter
    }

}


