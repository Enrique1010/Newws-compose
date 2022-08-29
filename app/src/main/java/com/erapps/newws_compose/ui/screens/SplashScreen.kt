package com.erapps.newws_compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erapps.newws_compose.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    moveOn: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        delay(1000)
        moveOn()
    }

    Splash()
}

@Composable
fun Splash(modifier: Modifier = Modifier) {
    Surface {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(96.dp, 96.dp),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.surface,
                    blendMode = BlendMode.Color
                ),
                painter = painterResource(id = R.drawable.ic_news),
                contentDescription = "splash",
                alignment = Alignment.Center
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = "News",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}