package com.example.appchinhthuc.Activity.Model

data class CoinMarketCapResponse(
    val data: List<CryptoCurrency>,
    val status: Status
)

data class Status(
    val timestamp: String,
    val error_code: Int,
    val error_message: String?,
    val elapsed: Int,
    val credit_count: Int
)

data class CryptoCurrency(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val cmc_rank: Int,
    val num_market_pairs: Int,
    val circulating_supply: Double,
    val total_supply: Double,
    val max_supply: Double?,
    val infinite_supply: Boolean,
    val last_updated: String,
    val date_added: String,
    val tags: List<String>,
    val platform: Platform?,
    val quote: Quote
)

data class Platform(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val token_address: String
)

data class Quote(
    val USD: CurrencyInfo
)

data class CurrencyInfo(
    val price: Double,
    val volume_24h: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val market_cap: Double,
    val last_updated: String
)

