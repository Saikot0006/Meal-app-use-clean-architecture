package com.example.mealapp.presentation.meal_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealapp.databinding.ViewHolderSearchListBinding
import com.example.mealapp.domain.model.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    var mealList = mutableListOf<Meal>()
    var mealCallBack :((Meal)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(ViewHolderSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mealList.size ?: 0
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position])
    }

    inner class MealViewHolder(val binding : ViewHolderSearchListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal){
            binding.meal = meal

           itemView.setOnClickListener {
               Log.d("mealCallBack", "initAdapter2: $meal")
               mealCallBack?.invoke(meal)
           }
        }
    }

    fun setContentList(list : MutableList<Meal>){
        mealList.clear()
        this.mealList.addAll(list)
        notifyDataSetChanged()
    }
}