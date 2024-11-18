package com.coin.coindemo.network

import com.coin.coindemo.repository.CoinRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    private const val BASE_URL = "https://37656be98b8f42ae8348e4da3ee3193f.api.mockbin.io"

    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor("")
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(provideAuthInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): CoinAPIService = retrofit.create(CoinAPIService::class.java)
}