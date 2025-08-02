package com.example.mealapp.presentation.meal_search

import com.example.mealapp.domain.model.Meal

data class MealSearchState(
    val list : List<Meal>? = null,
    val error : String = "",
    val loading: Boolean = false
)