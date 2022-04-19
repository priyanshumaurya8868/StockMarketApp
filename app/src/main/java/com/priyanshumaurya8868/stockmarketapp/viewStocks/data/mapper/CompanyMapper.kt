package com.priyanshumaurya8868.stockmarketapp.viewStocks.data.mapper

import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.local.CompanyListingEntity
import com.priyanshumaurya8868.stockmarketapp.viewStocks.data.remote.dto.CompanyInfoDto
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyInfo
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.model.CompanyListing


fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return  CompanyListing(
        name = name,
        symbol = symbol,
        exchange =  exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return  CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange =  exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}
