package com.example.mealapp.data.repository

import com.example.mealapp.data.model.MealsDTO
import com.example.mealapp.data.remote.MealApiService
import com.example.mealapp.domain.repository.MealDetailsRepository
import javax.inject.Inject

class GetMealDetailsRepository(private val apiService: MealApiService) : MealDetailsRepository {
    override suspend fun getMealDetails(id: String): MealsDTO {
        return apiService.getMealDetails(id)
    }
}