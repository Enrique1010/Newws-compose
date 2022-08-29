package com.erapps.newws_compose.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(): ViewModel() {

    private val _searchWidgetState = mutableStateOf(SearchWidgetState.Closed)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}

enum class SearchWidgetState {
    Opened,
    Closed
}