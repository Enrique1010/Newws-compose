package com.erapps.newws_compose.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.erapps.newws_compose.R
import com.erapps.newws_compose.ui.navigation.BottomNavGraph
import com.erapps.newws_compose.utils.isScrollingUp
import kotlinx.coroutines.launch

@Composable
fun LandingPage(landingPageViewModel: LandingPageViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val lazyState = rememberLazyListState()

    //searchBarState
    val searchWidgetState by landingPageViewModel.searchWidgetState
    val searchTextState by landingPageViewModel.searchTextState

    Scaffold(
        topBar = {
            if (lazyState.isScrollingUp() || lazyState.firstVisibleItemIndex == 0) {
                if (currentRoute != BottomNavItem.Search.route) {
                    landingPageViewModel.updateSearchWidgetState(SearchWidgetState.Closed)
                    DefaultAppBar(isSearchScreen = false) {}
                } else {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            landingPageViewModel.updateSearchTextState(it)
                        },
                        onCLoseClicked = {
                            landingPageViewModel.updateSearchWidgetState(SearchWidgetState.Closed)
                        },
                        onSearchTriggered = {
                            landingPageViewModel.updateSearchWidgetState(SearchWidgetState.Opened)
                        }
                    )
                }
            }
        },
        bottomBar = {
            BottomNav(navController, lazyState, currentRoute)
        }
    ) {
        BottomNavGraph(navController, lazyState, searchTextState)
    }
}

@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCLoseClicked: () -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.Closed -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.Opened -> {
            SearchBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCLoseClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    isSearchScreen: Boolean = true,
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.app_name_title)) },
        actions = {
            if (isSearchScreen){
                IconButton(onClick = { onSearchClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.surface
    ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = { SearchBarText(modifier) },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize),
            singleLine = true,
            leadingIcon = { SearchBarIcon(modifier) {} },
            trailingIcon = {
                SearchBarIcon(modifier, false) {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    }
                    onCloseClicked()
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onSecondary.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Composable
private fun SearchBarIcon(
    modifier: Modifier,
    leading: Boolean = true,
    onClickIcon: () -> Unit
) {
    IconButton(
        modifier = if (leading) modifier.alpha(ContentAlpha.medium) else modifier,
        onClick = { onClickIcon() }
    ) {
        Icon(
            imageVector = if (leading) Icons.Default.Search else Icons.Default.Close,
            contentDescription = if (leading) "Search Icon" else "Close Icon",
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun SearchBarText(modifier: Modifier) {
    Text(
        modifier = modifier.alpha(ContentAlpha.medium),
        text = "Search News...",
        color = MaterialTheme.colors.onSecondary
    )
}

@Composable
fun BottomNav(
    navController: NavHostController,
    lazyListState: LazyListState,
    currentRoute: String?
) {

    val coroutineScope = rememberCoroutineScope()

    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Top
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.LightGray,
                onClick = {
                    if (currentRoute == item.route) {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}