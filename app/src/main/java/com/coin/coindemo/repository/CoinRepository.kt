package com.coin.coindemo.repository
import com.coin.coindemo.data.GetCoinResponse
import com.coin.coindemo.network.CoinAPIService
import javax.inject.Inject

class CoinRepository @Inject constructor(private val apiService: CoinAPIService) {
    suspend fun fetchCoins() = apiService.getCoins()
}