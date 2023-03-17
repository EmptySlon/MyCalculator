package com.example.mycalculator.domain

import androidx.lifecycle.LiveData

interface SettingRepository {

    fun getSettingApp(): LiveData<SettingApp>

    suspend fun setSettingApp(number: Int)

}