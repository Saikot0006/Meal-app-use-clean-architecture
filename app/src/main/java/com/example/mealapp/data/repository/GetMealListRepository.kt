package com.example.mealapp.data.repository

import com.example.mealapp.data.model.MealsDTO
import com.example.mealapp.data.remote.MealApiService
import com.example.mealapp.domain.repository.MealListRepository

class GetMealListRepository(private val apiService: MealApiService) : MealListRepository {
    override suspend fun getMealList(s: String): MealsDTO {
        return apiService.getMealList(s)
    }
}