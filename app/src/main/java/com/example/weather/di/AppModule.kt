package com.example.weather.di

import android.util.Log
import com.example.weather.data.remote.WeatherApi
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.data.repositoryImp.WeatherRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**private val headersInterceptor = Interceptor { chain ->
        var request = chain.request()

        request = chain.request().newBuilder()
                .header("Connection", "keep-alive")
                .header("Vary", "Accept-Encoding")
                .header("CDN-PullZone", "93447")
                .header("CDN-Uid", "8fa3a04a-75d9-4707-8056-b7b33c8ac7fe")
                .header("CDN-RequestCountryCode", "GB")
                .header("Age", "0")
                .header("x-weatherapi-qpm-left", "4999998")
                .header("CDN-ProxyVer", "1.04")
                .header("CDN-RequestPullSuccess", "True")
                .header("CDN-RequestPullCode", "200")
                .header("CDN-CachedAt", "05/30/2024 21:04:55")
                .header("CDN-EdgeStorageId", "864")
                .header("CDN-Status", "200")
                .header("CDN-RequestId", "420fb73c41e1a912c34066d37fd4192e")
                .header("CDN-Cache", "MISS")
                .header("Accept-Ranges", "bytes")
                .header("Cache-Control", "public, max-age=180")
                .header("Content-Type", "application/json")
                .header("Date", "Thu, 30 May 2024 21:04:55 GMT")
                .header("Server", "BunnyCDN-DE1-1053")
                .header("Via", "1.1 varnish (Varnish/6.0)")
            .build()

        logHeaders(request.headers)

        chain.proceed(request)
        }*/

    fun logHeaders(headers: Headers) {
        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)
            Log.d("Header", "$name: $value")
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
//            .addInterceptor(headersInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherApi(client: OkHttpClient): WeatherApi {
        // Replace with your actual base URL
        val baseUrl = "https://api.weatherapi.com/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImp(api)
    }
}
