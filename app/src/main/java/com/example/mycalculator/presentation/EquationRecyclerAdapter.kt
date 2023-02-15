package com.example.mycalculator.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculator.databinding.ItemEquationBinding
import com.example.mycalculator.domain.Equation


class EquationRecyclerAdapter : RecyclerView.Adapter<EquationRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemEquationBinding) : RecyclerView.ViewHolder(binding.root)

    var equationList = listOf<Equation>()
        set(value) {
            val callback = EquationListDiffCallback(equationList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onEquationClickListener: ((Equation) -> Unit)? = null
    var onEquationLongClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemEquationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val equation = equationList[position]
        holder.binding.equation = equation
        holder.binding.root.setOnClickListener {
            onEquationClickListener?.invoke(equation)
        }
        holder.binding.root.setOnLongClickListener {
            onEquationLongClickListener?.invoke(equation.id)
            true
        }
    }

    override fun getItemCount(): Int = equationList.size

}