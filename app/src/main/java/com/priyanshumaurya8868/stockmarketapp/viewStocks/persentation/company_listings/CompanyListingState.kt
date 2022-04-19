package com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_listings

import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyListing

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)