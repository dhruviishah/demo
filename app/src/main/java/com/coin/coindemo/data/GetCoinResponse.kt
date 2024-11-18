package com.coin.coindemo.data

import com.google.gson.annotations.SerializedName

data class GetCoinResponse(
    val name: String,
    val symbol: String,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    val type: String
)