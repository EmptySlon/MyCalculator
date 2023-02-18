package com.example.mycalculator.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mycalculator.domain.Equation

class EquationListDiffCallback (
    val oldList: List<Equation>,
    val newList: List<Equation>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition].equation
        val oldItem = oldList[oldItemPosition].equation
        return newItem == oldItem
    }

}