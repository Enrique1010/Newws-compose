package com.erapps.newws_compose.ui.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erapps.newws_compose.ui.screens.search.SearchScreen
import com.erapps.newws_compose.ui.screens.top.TopScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    lazyState: LazyListState,
    searchText: String = ""
) {

    NavHost(navController = navController, startDestination = NavItem.Search.route) {
        composable(NavItem.Search) {
            SearchScreen(lazyState = lazyState, searchText = searchText)
        }
        composable(NavItem.Top) {
            TopScreen(lazyListState = lazyState)
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = navItem.route, arguments = navItem.args) {
        content(it)
    }
}