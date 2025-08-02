package com.example.mealapp.presentation.meal_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.common.Resource
import com.example.mealapp.domain.model.MealDetails
import com.example.mealapp.domain.use_case.MealDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(private val mealDetailsUseCae : MealDetailsUseCase) : ViewModel() {

    private val _mealDetails = MutableStateFlow<MealDetailsState>(MealDetailsState())
    val mealDetails : StateFlow<MealDetailsState> = _mealDetails

    fun getMealDetails(id : String){
       try {
           mealDetailsUseCae(id).onEach {
               when(it){
                   is Resource.Loading -> _mealDetails.value = MealDetailsState(loading = true)

                   is Resource.Error -> _mealDetails.value = MealDetailsState(error = it.message ?: "")

                   is Resource.Success -> _mealDetails.value = MealDetailsState(mealData = it.data)
               }
           }.launchIn(viewModelScope)
       }catch (e:Exception){}
    }
}