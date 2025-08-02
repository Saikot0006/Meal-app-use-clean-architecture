package com.example.mealapp.presentation.meal_details

import com.example.mealapp.domain.model.MealDetails

data class MealDetailsState(
    val mealData : MealDetails? = null,
    val error : String = "",
    val loading : Boolean = false
)
