package com.erapps.newws_compose.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erapps.newws_compose.data.models.Article
import com.erapps.newws_compose.data.source.search.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val newsRepository: ISearchRepository
) : ViewModel() {

    private val _searchListState = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchListState = _searchListState.asStateFlow()

    init {
        getArticleList("")
    }

    private fun getArticleList(searchText: String) = viewModelScope.launch {
        val query = searchText.ifEmpty { "the" }
        newsRepository.getUserByQuery(query).cachedIn(viewModelScope)
            .collectLatest { data ->
                _searchListState.update { data }
            }
    }

    fun updateSearchText(newValue: String) {
        getArticleList(newValue)
    }

}