package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elvo.domain.model.faq.Faq
import com.example.elvo.ui.viewmodels.faq.FaqUIState
import com.example.elvo.ui.viewmodels.faq.FaqViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun FAQScreen(faqViewModel: FaqViewModel = hiltViewModel()) {
    val state = faqViewModel.state
    val context = LocalContext.current
    val faqItems = remember { mutableStateOf<List<Faq>>(emptyList()) }
    LaunchedEffect(Unit) {
        state.collectLatest { state ->
            when(state){
                is FaqUIState.Default -> {}
                is FaqUIState.Error -> {
                    Toast.makeText(context, state.errorResId, Toast.LENGTH_LONG).show()
                }
                is FaqUIState.Success -> {
                    faqItems.value = state.data
                }
            }

        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(faqItems.value.size) { index ->
                val item = faqItems.value[index]
                ExpandableFAQItem(question =  item.question, answer =  item.answer)
            }
        }
    }
}


@Composable
fun ExpandableFAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (expanded) "–" else "+",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(24.dp)

            )
        }

        AnimatedVisibility(visible = expanded) {
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.DarkGray
            )
        }
    }
}
