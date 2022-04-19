package com.priyanshumaurya8868.stockmarketapp.viewStocks.di

import android.app.Application
import androidx.room.Room
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.local.StockDatabase
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStockApi(): StockApi {
        return Retrofit
            .Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun providesDb(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        )
            .build()
    }
}