package com.example.convert.data.server

import com.example.convert.data.toDomainCurrency
import com.example.data.source.RemoteDataSource
import com.example.domain.Currency

class CurrencyDataSource : RemoteDataSource {

    override suspend fun getLatestCurrencies(base: String): Currency {
        return CurrencyDb.service
            .getAllCurrencies("880dac7885125117800c4d9e0e60fe44",base).await()
            .toDomainCurrency()
    }
}