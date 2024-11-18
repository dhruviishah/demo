package com.coin.coindemo.data

enum class CoinState(val value: String) {
    ACTIVE("is_active"),
    NEW("is_new"),
    ALL(""),
    TYPE_COIN("coin"),
    TYOE_TOKEN("token")
}