package com.priyanshumaurya8868.stockmarketapp.viewStocks.data.repository

import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.csv.CSVParser
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.local.StockDatabase
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.mapper.toCompanyInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.mapper.toCompanyListing
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.mapper.toCompanyListingEntity
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.remote.StockApi
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.repository.StockRepository
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyListing
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.IntradayInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val db: StockDatabase,
    private val api: StockApi,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser : CSVParser<IntradayInfo>
) : StockRepository {
    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchfromRemote: Boolean,
        query: String
    ) = flow<Resource<List<CompanyListing>>> {
        emit(Resource.Loading(true))
        val localListings = dao.searchCompanyListing(query)
        emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))

        val isDbEmpty = localListings.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchfromRemote
        if(shouldJustLoadFromCache){
            emit(Resource.Loading(false))
            return@flow
        }
        val remoteListing = try{
            val  response = api.getListings()
            companyListingParser.parse(response.byteStream())
        }catch (e :IOException){
            e.printStackTrace()
            null
        }catch (e:HttpException){
            e.printStackTrace()
            null
        }

        remoteListing?.let { list->
            dao.clearCompanyListings()
            dao.insertCompanyListings(list.map { it.toCompanyListingEntity() })
            emit(Resource.Success(
                data =dao
                    .searchCompanyListing("")
                    .map { it.toCompanyListing() }
            ))
            emit(Resource.Loading(false))

        }

    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
       return try {
           val response = api.getIntradayInfo(symbol)
           val results = intradayInfoParser.parse(response.byteStream())
           Resource.Success(results)
       } catch(e: IOException) {
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load intraday info"
           )
       } catch(e: HttpException) {
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load intraday info"
           )
       }

    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
       return  try {
           val response = api.getCompanyInfo(symbol = symbol)
           Resource.Success(response.toCompanyInfo())
       } catch(e: IOException) {
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load intraday info"
           )
       } catch(e: HttpException) {
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load intraday info"
           )
       }
    }


}