package com.example.mycalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculator.databinding.ItemEquationBinding
import com.example.mycalculator.model.EquationOld


class EquationRecyclerAdapter(var equationOlds: List<EquationOld>) :
    RecyclerView.Adapter<EquationRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding:  ItemEquationBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEquationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val equation = equationOlds[position]
        with(holder.binding){
            txEquation.text = "${equation.equation}="
            txAnswer.text = equation.calculateAnswer()
        }



    }

    override fun getItemCount(): Int = equationOlds.size

}