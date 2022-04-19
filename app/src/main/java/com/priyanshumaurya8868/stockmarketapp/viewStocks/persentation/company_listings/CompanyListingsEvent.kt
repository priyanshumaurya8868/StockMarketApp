package com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_listings

sealed class CompanyListingsEvent{
    object  Refresh:CompanyListingsEvent()
    data class OnSearchQueryChange(val query:String):CompanyListingsEvent()
}
