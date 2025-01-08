package com.example.weather.data.repositoryImp

import com.example.weather.data.remote.WeatherApi
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.model.ResponseModel


class WeatherRepositoryImp  constructor(
    private val  api : WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(apiKey : String, city : String): ResponseModel {
        return api.getWeather(apiKey,city)
    }
}