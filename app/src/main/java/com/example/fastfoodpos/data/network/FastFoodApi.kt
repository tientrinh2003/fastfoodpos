package com.example.fastfoodpos.data.network

import com.example.fastfoodpos.domain.model.FoodItem
import retrofit2.Response
import retrofit2.http.GET

interface FastFoodApi {
    @GET("menu")
    suspend fun getFoodItems(): Response<List<FoodItem>>
}
