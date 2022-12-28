package com.example.mycalculator.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculator.R
import com.example.mycalculator.databinding.ItemEquationBinding
import com.example.mycalculator.domain.Equation


class EquationRecyclerAdapter : RecyclerView.Adapter<EquationRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemEquationBinding) : RecyclerView.ViewHolder(binding.root)

    var equationList = listOf<Equation>()
        set(value) {
//
//           if (equationList.isNotEmpty()) {
//               val a = value.last() != equationList.last()
//           }
//            if (equationList.isNotEmpty() && value.last()== equationList.last()) {
//                return
//            }

            val callback = EquationListDiffCallback(equationList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemClickListener: ((Equation) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemEquationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val equation = equationList[position]
        with(holder.binding) {
            txEquation.text = root.context.getString(R.string.equal_in_rv, equation.equation)
            txAnswer.text = equation.answer
            root.setOnClickListener {
                onShopItemClickListener?.invoke(equation)
            }
        }
    }

    override fun getItemCount(): Int = equationList.size

}