package com.example.appchinhthuc.Activity

import com.example.appchinhthuc.Activity.Model.CoinMarketCapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketCapService {
    @Headers("X-CMC_PRO_API_KEY: b84b7a7f-d8ae-4716-8ba0-62cdb47da766")
    @GET("v1/cryptocurrency/listings/latest")
    fun getLatestListings(@Query("start") start: Int,
                          @Query("limit") limit: Int,
                          @Query("convert") convert: String): Call<CoinMarketCapResponse>
}