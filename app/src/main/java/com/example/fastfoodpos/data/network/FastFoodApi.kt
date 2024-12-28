package com.example.fastfoodpos.data.network

import com.example.fastfoodpos.data.network.DTO.FoodItemDTO
import retrofit2.Response
import retrofit2.http.GET

interface FastFoodApi {
    @GET("menu")
    suspend fun getFoodItems(): Response<List<FoodItemDTO>>
}
