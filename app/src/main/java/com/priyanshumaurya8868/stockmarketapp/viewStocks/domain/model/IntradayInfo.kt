package com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model

import java.time.LocalDateTime


data class IntradayInfo(
    val date: LocalDateTime,
    val close: Double
)