package com.example.appchinhthuc.Activity.Model

import com.squareup.moshi.Json

data class CryptoLastTrade(
    @Json(name = "status") val status: String,
    @Json(name = "request_id") val requestId: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "last") val last: LastTrade
)

data class LastTrade(
    @Json(name = "price") val price: Double,
    @Json(name = "size") val size: Double,
    @Json(name = "exchange") val exchange: Int,
    @Json(name = "conditions") val conditions: List<Int>,
    @Json(name = "timestamp") val timestamp: Long
)
