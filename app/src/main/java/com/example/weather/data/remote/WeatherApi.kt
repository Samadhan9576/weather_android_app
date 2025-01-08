package com.example.weather.data.remote

import com.example.weather.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey : String,
        @Query("q") city : String
    ): ResponseModel
}