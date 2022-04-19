package com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshumaurya8868.stockmarketapp.viewStocks.domain.repository.StockRepository
import com.priyanshumaurya8868.stockmarketapp.viewStocks.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getCompanyListings(query = event.query)
                }
            }
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(Locale.getDefault()),
        fetchFromRemote: Boolean = false
    ) = viewModelScope.launch {
        repository.getCompanyListings(query = query, fetchfromRemote = fetchFromRemote)
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listing ->
                            state = state.copy(
                                companies = listing
                            )
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
    }
}