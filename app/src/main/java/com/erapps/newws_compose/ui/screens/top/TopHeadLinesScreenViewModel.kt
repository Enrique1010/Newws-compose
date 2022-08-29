package com.erapps.newws_compose.ui.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erapps.newws_compose.data.models.Article
import com.erapps.newws_compose.data.source.top.ITopHeadLinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadLinesScreenViewModel @Inject constructor(
    private val topHeadLinesRepository: ITopHeadLinesRepository
) : ViewModel() {

    private val _topHeadlineState = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val topHeadLineState = _topHeadlineState.asStateFlow()

    init {
        getNewsByCategory("")
    }

    fun getNewsByCategory(value: String) = viewModelScope.launch {
        val category = value.ifEmpty { "general" }
        topHeadLinesRepository.getTopNewsByCategory(category).cachedIn(viewModelScope)
            .collectLatest { data ->
                _topHeadlineState.update { data }
            }
    }
}