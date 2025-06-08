package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.elvo.R
import com.example.elvo.domain.model.popular.PopularItem
import com.example.elvo.ui.viewmodels.popular.PopularUIState
import com.example.elvo.ui.viewmodels.popular.PopularViewModel
import kotlinx.coroutines.flow.collectLatest

@Preview
@Composable
fun HomeScreen(popularViewModel: PopularViewModel = hiltViewModel()) {
    val state = popularViewModel.state
    val context = LocalContext.current
    val popularItems = remember { mutableStateOf<List<PopularItem>>(emptyList()) }
    LaunchedEffect(Unit) {
        state.collectLatest { state ->
            when(state){
                is PopularUIState.Default -> {}
                is PopularUIState.Error -> {
                    Toast.makeText(context, state.errorResId, Toast.LENGTH_LONG).show()
                }
                is PopularUIState.Success -> {
                    popularItems.value = state.data
                }
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( Color(0xFFF7FAFC))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Рекомендованное",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(popularItems.value.size) { index ->
                val item = popularItems.value[index]
                ProductCard(name = item.title, imageUrl = item.url)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "О приложении",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.elvo_logo),
                contentDescription = "Логотип ELVO",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "ELVO",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                )
                Text(
                    text = "Evolution meets Velocity",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFF0B57D0),
                        fontSize = 14.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ELVO — это приложение, которое объединяет скорость и технологичность. Мы помогаем вам доставлять и отслеживать товары с Poizon быстро, удобно и без стресса. Наш интерфейс прост и интуитивен, а поддержка надёжна. Всё ради вашего комфорта.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF1A1A1A),
                lineHeight = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

    }
}


@Composable
fun ProductCard(name: String, imageUrl: String) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}



