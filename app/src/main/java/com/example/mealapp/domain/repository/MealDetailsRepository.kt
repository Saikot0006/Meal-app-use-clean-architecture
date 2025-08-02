package com.example.mealapp.domain.repository

import com.example.mealapp.data.model.MealsDTO
import retrofit2.http.Query

interface MealDetailsRepository {
    suspend fun getMealDetails(id : String) : MealsDTO
}