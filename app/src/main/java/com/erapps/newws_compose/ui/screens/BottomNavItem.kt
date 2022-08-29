package com.erapps.newws_compose.ui.screens

import com.erapps.newws_compose.R
import com.erapps.newws_compose.ui.navigation.NavItem

sealed class BottomNavItem(var route: String, var icon: Int, var title: String) {
    object Top: BottomNavItem(NavItem.Top.route, R.drawable.ic_trending_up, "Top")
    object Search: BottomNavItem(NavItem.Search.route, R.drawable.ic_search, "Search")
}