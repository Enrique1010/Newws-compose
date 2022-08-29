package com.erapps.newws_compose.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map { navArgument(name = it.key) { type = it.navType } }

    //navigation objects
    //main nav
    object Splash: NavItem("splash")
    object LandingPage: NavItem("landing_page")
    // bottom nav
    object Search: NavItem("search")
    object Top: NavItem("top")

}

enum class NavArgs(val key: String, val navType: NavType<*>) {}