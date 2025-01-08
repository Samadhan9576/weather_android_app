package com.example.weather.useCases

import com.example.weather.data.Resource
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.model.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class WeatherUseCase  @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(apiKey: String, city: String): Flow<Resource<ResponseModel>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getWeather(apiKey,city)
            emit(Resource.Success(response))
        }catch (e : HttpException){
            emit(Resource.Error(e.localizedMessage ?: "an unexpected message occured"))
        }catch (e : IOException){
            emit(Resource.Error("Couldn't reach server . check your internet connection."))
        }
    }
}