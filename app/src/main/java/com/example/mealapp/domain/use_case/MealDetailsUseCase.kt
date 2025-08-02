package com.example.mealapp.domain.use_case

import com.example.mealapp.common.Resource
import com.example.mealapp.data.model.toDomainMealDetails
import com.example.mealapp.domain.model.MealDetails
import com.example.mealapp.domain.repository.MealDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MealDetailsUseCase @Inject constructor(private val mealDetailsRepository: MealDetailsRepository) {

    operator fun invoke(id : String) : Flow<Resource<MealDetails>> = flow {
        try {
            emit(Resource.Loading())

            val respone = mealDetailsRepository.getMealDetails(id).meals[0].toDomainMealDetails()

            emit(Resource.Success(respone))

        }catch (e : Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        }catch (e : IOException){
            emit(Resource.Error(message = e.localizedMessage ?: "Check your internet connection"))
        }
    }
}