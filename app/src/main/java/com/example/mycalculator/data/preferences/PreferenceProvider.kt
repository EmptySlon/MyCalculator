package com.example.mycalculator.data.preferences

import android.content.Context
import com.example.mycalculator.domain.SettingApp

private const val NAME_SHARED_PREF = "My Shared Preferences"
private const val NUMBER_AFTER_COMMA = "number after comma"

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val sharedPref = appContext.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)

    fun getNumberAfterComma(): Int = sharedPref.getInt(
        NUMBER_AFTER_COMMA,
        SettingApp.DEFAULT_NUMBER_AFTER_COMMA
    )

    fun setNumberAfterComma(number: Int) {
        sharedPref.edit().putInt(NUMBER_AFTER_COMMA, number).apply()
    }
}