package com.erapps.newws_compose.ui.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erapps.newws_compose.R
import com.erapps.newws_compose.data.models.Article
import com.erapps.newws_compose.utils.toDate
import java.text.DateFormat

@Composable
fun ArticleList(
    list: LazyPagingItems<Article>,
    lazyState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.padding(),
        state = lazyState
    ) {
        itemsIndexed(list) { _, article ->
            ArticleListItem(
                article = article
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListItem(
    article: Article?
) {
    val context = LocalContext.current
    Card(
        onClick = { openInCustomTab(context, article?.url.toString()) },
        modifier = Modifier
            .padding(8.dp)
            .border(BorderStroke(1.dp, MaterialTheme.colors.surface), shape = Shapes().small)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(article?.urlToImage.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = article?.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            CardTopSection(article)
            CardBottomSection(article, context)
        }
    }
}

@Composable
private fun CardTopSection(article: Article?) {
    Text(
        text = article?.title.toString(),
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.h6,
    )
    Row(Modifier.padding(start = 16.dp, end = 16.dp)) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            article?.description?.let {
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
private fun CardBottomSection(
    article: Article?,
    context: Context
) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            // Buttons
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    text = article?.source?.name.toString(),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_dot),
                    contentDescription = null,
                    modifier = Modifier
                        .size(4.dp)
                        .align(alignment = Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colors.onSecondary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = run {
                        val date = article?.publishedAt.toString().toDate()
                        DateFormat.getDateInstance(DateFormat.MEDIUM).format(date!!)
                    }
                )
            }
            // Icons
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                IconButton(onClick = {
                    shareArticleUrl(
                        context,
                        article?.url.toString()
                    )
                }) {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
            }
        }
    }
}

private fun shareArticleUrl(context: Context, url: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share with")
    ContextCompat.startActivity(context, shareIntent, null)
}

private fun openInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder().build()
    builder.launchUrl(context, Uri.parse(url))
}