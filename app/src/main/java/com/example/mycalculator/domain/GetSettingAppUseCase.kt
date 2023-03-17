package com.example.mycalculator.domain

import androidx.lifecycle.LiveData


class GetSettingAppUseCase(private val settingRepository: SettingRepository) {
    fun getSettingApp() : LiveData<SettingApp> {
        return  settingRepository.getSettingApp()
    }
}