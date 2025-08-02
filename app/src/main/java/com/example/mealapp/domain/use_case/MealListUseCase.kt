package com.example.mealapp.domain.use_case

import com.example.mealapp.common.Resource
import com.example.mealapp.data.model.toDomainMeal
import com.example.mealapp.data.repository.GetMealListRepository
import com.example.mealapp.domain.model.Meal
import com.example.mealapp.domain.repository.MealListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MealListUseCase @Inject constructor(private val mealListRepository: MealListRepository) {

    operator fun invoke(s : String) : Flow<Resource<List<Meal>>> = flow {
        try {
            emit(Resource.Loading())

            val response = mealListRepository.getMealList(s)
            val list = if(response.meals.isEmpty()) emptyList<Meal>() else response.meals.map { it.toDomainMeal() }

            emit(Resource.Success(data = list))

        }catch (e : Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        }catch (e : IOException){
            emit(Resource.Error(message = e.localizedMessage ?: "Check your internet connection"))
        }
    }
}