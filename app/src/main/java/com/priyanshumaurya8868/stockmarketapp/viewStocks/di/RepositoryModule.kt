package com.priyanshumaurya8868.stockmarketapp.viewStocks.di

import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.csv.CSVParser
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.csv.CompanyListingParser
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.csv.IntradayInfoParser
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.repository.StockRepositoryImpl
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.repository.StockRepository
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyListing
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.IntradayInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyParser(
        parser : CompanyListingParser,
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        parser : IntradayInfoParser,
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
// this class  is there because hilt is not smart enough to provide  the implementation  of interface that we used