package com.example.mycalculator.model

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


class CustomEditText : AppCompatEditText {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
    }

    override fun setText(text: CharSequence?, type: BufferType?) {

        super.setText(text, type)
    }

    override fun setSelection(index: Int) {
//        val positionDifference =
//            text.toString()
//                .dropLast(text.toString().length - index )
//                .count { it == ' ' }
//        super.setSelection(index - positionDifference)
        super.setSelection(index)

    }

    override fun getSelectionEnd(): Int {
//        val cursorPosition = super.getSelectionEnd()
//        val positionDifference =
//            text.toString()
//                .dropLast(text.toString().length - cursorPosition + 1)
//                .count { it == ' ' }
////        val positionDifference = addSpaceToString(text.toString()).length - text.toString().length
//        return cursorPosition - positionDifference
        return super.getSelectionEnd()
    }

    private fun deleteSpace(equation: String): String = equation.filter { it != ' ' }


    private fun addSpaceToString(equation: String): String {
        val listOldSubstring =
            equation
                .split(Regex("[()x+/-]"))
                .map {
                    val filteredString =
                        if (it.contains('.')) it.dropLast(it.length - it.indexOf("."))
                        else it
                    if (filteredString.length > 3) filteredString else ""
                }
                .filter { it.isNotBlank() }

        val listNewSubstring = listOldSubstring.map { text ->
            text
                .reversed()
                .chunked(3)
                .map { it.reversed() }
                .reversed()
                .joinToString(" ")
                .trim()

        }
        var newEquation = equation
        for ((index, oldString) in listOldSubstring.withIndex()) {
            newEquation = newEquation.replace(oldString, listNewSubstring[index])
        }
        return newEquation
    }
}