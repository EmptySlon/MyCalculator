package com.example.mycalculator.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mycalculator.data.SettingRepositoryImpl
import com.example.mycalculator.domain.GetSettingAppUseCase
import com.example.mycalculator.domain.SetSettingAppUseCase
import com.example.mycalculator.domain.SettingApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val settingRepository = SettingRepositoryImpl(application)

    private val getSettingAppUseCase = GetSettingAppUseCase(settingRepository)
    private val setSettingAppUseCase = SetSettingAppUseCase(settingRepository)

    val settingApp: LiveData<SettingApp>
        get() = getSettingAppUseCase.getSettingApp()

    fun changeNumberAfterComma() {
        viewModelScope.launch {
            delay(30000)
            setSettingAppUseCase.setSettingApp(5)
            delay(30000)
            setSettingAppUseCase.setSettingApp(7)
            delay(30000)
            setSettingAppUseCase.setSettingApp(1)
        }
    }
}