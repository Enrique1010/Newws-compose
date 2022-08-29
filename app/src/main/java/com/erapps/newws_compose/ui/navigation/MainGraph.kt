package com.erapps.newws_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erapps.newws_compose.ui.screens.SplashScreen
import com.erapps.newws_compose.ui.screens.LandingPage

@Composable
fun MainGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Splash.baseRoute) {
        composable(route = NavItem.Splash.baseRoute){
            SplashScreen {
                navController.popBackStack()
                navController.navigate(NavItem.LandingPage.baseRoute)
            }
        }
        composable(NavItem.LandingPage.baseRoute){
            LandingPage()
        }
    }
}