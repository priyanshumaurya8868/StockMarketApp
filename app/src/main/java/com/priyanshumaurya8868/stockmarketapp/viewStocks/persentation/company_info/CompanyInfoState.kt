package com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_info

import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val isLoading: Boolean = false,
    val company: CompanyInfo? = null,
    val error: String? = null
)
