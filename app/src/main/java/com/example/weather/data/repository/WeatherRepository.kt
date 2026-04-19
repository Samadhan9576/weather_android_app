package com.example.weather.data.repository

import com.example.weather.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(apiKey : String, city : String) : WeatherResponse
    suspend fun getWeather(latitude:Double,longitude:Double, apiKey: String) : WeatherResponse
}