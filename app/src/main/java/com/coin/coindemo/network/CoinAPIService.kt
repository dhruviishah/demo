package com.coin.coindemo.network

import com.coin.coindemo.data.GetCoinResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CoinAPIService {

    @GET(".")
    suspend fun getCoins(): Response<List<GetCoinResponse>>
}