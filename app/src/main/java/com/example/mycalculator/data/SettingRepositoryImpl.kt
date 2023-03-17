package com.example.mycalculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycalculator.data.preferences.PreferenceProvider
import com.example.mycalculator.domain.SettingApp
import com.example.mycalculator.domain.SettingRepository

class SettingRepositoryImpl(private val application: Application) : SettingRepository {
    private val settingAppLD = MutableLiveData<SettingApp>()
    private val prefProvider = PreferenceProvider(application)
    private var settingApp: SettingApp = getSettingFromPref()


    override fun getSettingApp(): LiveData<SettingApp> {
        settingAppLD.value = settingApp.copy()
        return  settingAppLD
    }

    override suspend fun setSettingApp(number: Int) {
        prefProvider.setNumberAfterComma(number)
        settingApp = getSettingFromPref()
        settingAppLD.value = settingApp.copy()
    }

    private fun getSettingFromPref() : SettingApp {
        return SettingApp(
            prefProvider.getNumberAfterComma()
        )
    }


}