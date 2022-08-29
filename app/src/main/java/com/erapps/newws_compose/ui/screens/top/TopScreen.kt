package com.erapps.newws_compose.ui.screens.top

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.erapps.newws_compose.ui.shared.ArticleList
import java.util.*

@Composable
fun TopScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    viewModel: TopHeadLinesScreenViewModel = hiltViewModel()
) {
    val list = viewModel.topHeadLineState.collectAsLazyPagingItems()
    val selectedHeadline = mutableStateOf(getHeadLine("general"))

    Surface {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryChipGroup(
                modifier = modifier,
                selectedHeadline = selectedHeadline.value
            ) {
                selectedHeadline.value = getHeadLine(it)
                viewModel.getNewsByCategory(it)
            }
            if (list.itemCount == 0) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            ArticleList(list = list, lazyState = lazyListState)
        }
    }
}

@Composable
fun CategoryChipGroup(
    modifier: Modifier,
    headLines: List<TopHeadline> = getAllHeadLines(),
    selectedHeadline: TopHeadline? = null,
    onSelectedChanged: (String) -> Unit
) {
    Column(modifier = modifier.padding(8.dp)) {
        LazyRow {
            items(headLines) { headline ->
                Chip(
                    modifier = modifier,
                    name = headline.headline,
                    isSelected = selectedHeadline == headline,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier,
    name: String,
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Surface(
        modifier = modifier.padding(4.dp),
        elevation = 8.dp,
        shape = CircleShape,
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    ) {
        Row(modifier = modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
        ) {
            if (isSelected){
                Icon(
                    modifier = modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Check,
                    contentDescription = name,
                    tint = MaterialTheme.colors.onSecondary
                )
            }
            Text(
                text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}

enum class TopHeadline(val headline: String) {
    General("general"),
    Entertainment("entertainment"),
    Sports("sports"),
    Science("science"),
    Technology("technology"),
    Health("health"),
    Business("business"),
}

fun getAllHeadLines(): List<TopHeadline> {
    return listOf(
        TopHeadline.General,
        TopHeadline.Entertainment,
        TopHeadline.Sports,
        TopHeadline.Science,
        TopHeadline.Technology,
        TopHeadline.Health,
        TopHeadline.Business
    )
}

fun getHeadLine(value: String): TopHeadline? {
    val map = TopHeadline.values().associateBy(TopHeadline::headline)
    return map[value]
}