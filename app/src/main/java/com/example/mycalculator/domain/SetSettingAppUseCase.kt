package com.example.mycalculator.domain

import androidx.lifecycle.LiveData


class SetSettingAppUseCase(private val settingRepository: SettingRepository) {
    suspend fun setSettingApp(number: Int) {
        settingRepository.setSettingApp(number)
    }
}