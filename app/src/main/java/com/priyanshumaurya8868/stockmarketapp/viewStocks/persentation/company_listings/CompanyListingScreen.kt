package com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.company_info.CompanyInfoScreen
import com.priyanshumaurya8868.stockmarketapp.viewStocks.persentation.destinations.CompanyInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun CompanyListingScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    val state = viewModel.state
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search...") },
            maxLines = 1,
            singleLine = true
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.companies.size) { i ->
                    val  company = state.companies[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navigator.navigate(CompanyInfoScreenDestination(company.symbol))
                            }
                    )
                    if (i < state.companies.size) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

            }

        }
    }
}