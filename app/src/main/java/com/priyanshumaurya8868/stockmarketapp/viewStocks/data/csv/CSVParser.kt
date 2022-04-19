package com.priyanshumaurya8868.stockmarketapp.viewStocks.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream : InputStream):List<T>
}