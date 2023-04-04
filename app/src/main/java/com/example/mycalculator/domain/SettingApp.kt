package com.example.mycalculator.domain

data class SettingApp(
    var NumberAfterComma: Int = DEFAULT_NUMBER_AFTER_COMMA
) {
    companion object {
        const val DEFAULT_NUMBER_AFTER_COMMA = 3
    }
}
