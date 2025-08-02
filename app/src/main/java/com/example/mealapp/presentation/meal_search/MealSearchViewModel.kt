package com.example.mealapp.presentation.meal_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.common.Resource
import com.example.mealapp.domain.model.Meal
import com.example.mealapp.domain.use_case.MealDetailsUseCase
import com.example.mealapp.domain.use_case.MealListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MealSearchViewModel @Inject constructor(private val mealSearchUseCase: MealListUseCase) : ViewModel(){

    private val _mealSearchList = MutableStateFlow<MealSearchState>(MealSearchState())
    val mealSearchList : StateFlow<MealSearchState> = _mealSearchList

    fun getMealList(s : String){
        try {
            mealSearchUseCase(s).onEach {
                when(it){
                    is Resource.Loading -> {
                        _mealSearchList.value = MealSearchState(loading = true)
                    }

                    is Resource.Error -> {
                        _mealSearchList.value = MealSearchState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        _mealSearchList.value = MealSearchState(list = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }catch (e:Exception){}
    }
}