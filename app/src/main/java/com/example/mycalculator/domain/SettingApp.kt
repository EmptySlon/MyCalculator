package com.example.mycalculator.domain

private const val DEFAULT_NUMBER_AFTER_COMMA = 3
data class SettingApp(
    var NumberAfterComma: Int = DEFAULT_NUMBER_AFTER_COMMA
)
