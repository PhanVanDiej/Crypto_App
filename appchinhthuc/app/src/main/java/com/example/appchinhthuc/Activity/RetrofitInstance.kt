package com.example.appchinhthuc.Activity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CoinMarketCapService by lazy {
        retrofit.create(CoinMarketCapService::class.java)
    }
}