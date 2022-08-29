package com.erapps.newws_compose.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.erapps.newws_compose.ui.shared.ArticleList

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    lazyState: LazyListState,
    searchText: String
) {
    val list = viewModel.searchListState.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (list.itemCount == 0) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        viewModel.updateSearchText(searchText)
        ArticleList(list = list, lazyState)
    }
}