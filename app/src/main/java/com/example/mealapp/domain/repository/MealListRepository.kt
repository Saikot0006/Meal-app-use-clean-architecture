package com.example.mealapp.domain.repository

import com.example.mealapp.data.model.MealsDTO
import retrofit2.http.Query

interface MealListRepository {
    suspend fun getMealList(s : String) : MealsDTO
}