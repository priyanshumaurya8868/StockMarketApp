package com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.repository

import android.icu.text.DateFormatSymbols
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyListing
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.IntradayInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchfromRemote : Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol : String
    ):Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ):Resource<CompanyInfo>
}