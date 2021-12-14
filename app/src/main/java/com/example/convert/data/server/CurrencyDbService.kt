package com.example.convert.data.server

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyDbService {

    @GET("latest")
    fun getAllCurrencies(@Query("access_key") access_key:String,@Query("base") base: String) : Deferred<Currency>
}