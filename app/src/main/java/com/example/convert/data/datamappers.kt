package com.example.convert.data

import com.example.domain.Currency
import com.example.convert.data.database.Currency as DomainCurrency
import com.example.convert.data.server.Currency as ServerCurrency
import com.example.convert.data.server.Rate as ServerRate
import com.example.domain.Rate as DomainRate

fun Currency.toRoomCurrency(): DomainCurrency =
    DomainCurrency(
        id,
        getRateData(rates),
        base,
        date
    )

fun DomainCurrency.toDomainCurrency(): Currency =
    Currency(
        id,
        getRateData(this.rates),
        base,
        date
    )

fun ServerCurrency.toDomainCurrency(): Currency =
    Currency(
        id,
        getRateData(this.rates),
        base,
        date
    )



fun getRateData(rate: DomainRate?): DomainRate? {
    rate?.let {
        return DomainRate(
            it.rateId,
            it.eur,
            it.jpy,
            it.INR,
            it.gbp
        )
    }
    return null
}

fun getRateData(rate: ServerRate?): DomainRate? {
    rate?.let {
        return DomainRate(
            it.rateId,
            it.eur,
            it.jpy,
            it.brl,
            it.gbp
        )
    }
    return null
}