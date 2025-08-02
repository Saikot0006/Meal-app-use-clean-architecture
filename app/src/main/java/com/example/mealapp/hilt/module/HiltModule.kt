package com.example.mealapp.hilt.module

import com.example.mealapp.common.Constant
import com.example.mealapp.data.remote.MealApiService
import com.example.mealapp.data.repository.GetMealDetailsRepository
import com.example.mealapp.data.repository.GetMealListRepository
import com.example.mealapp.domain.repository.MealDetailsRepository
import com.example.mealapp.domain.repository.MealListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.BuildConfig
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMealApiService(okHttpClient : OkHttpClient) : MealApiService {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MealApiService::class.java)
    }

    @Provides
    fun provideMealListRepository(apiService: MealApiService) : MealListRepository {
        return GetMealListRepository(apiService)
    }

    @Provides
    fun provideMealDetailsRepository(apiService: MealApiService) : MealDetailsRepository {
        return GetMealDetailsRepository(apiService)
    }
}