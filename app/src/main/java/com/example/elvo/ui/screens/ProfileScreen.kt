package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.elvo.R
import com.example.elvo.ui.viewmodels.user.UserUIState
import com.example.elvo.ui.viewmodels.user.UserViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProfileScreen(navController: NavController,  viewModel: UserViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val username = remember { mutableStateOf("Пользователь") }
    val avatarUrl = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { state ->
            when (state) {
                is UserUIState.Success -> {
                    username.value = state.userInfo.username ?: "Пользователь"
                    avatarUrl.value = state.userInfo.avatarUrl
                }

                is UserUIState.Error -> {
                    Toast
                        .makeText(context, context.getString(state.errorResId), Toast.LENGTH_LONG)
                        .show()
                }

                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            val avatarPainter = if (!avatarUrl.value.isNullOrEmpty()) {
                rememberAsyncImagePainter(avatarUrl.value)
            } else {
                painterResource(id = com.example.elvo.R.drawable.avatar)
            }

            Image(
                painter = avatarPainter,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF4DED3))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = username.value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileOption(
                    icon = Icons.Default.Face,
                    label = "Получатели",
                    onClick = { navController.navigate("recipient_list") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileOption(
                    icon = Icons.Default.Add,
                    label = "Добавить заказ",
                    onClick = { navController.navigate("order_add") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileOption(
                    icon = Icons.Default.Build,
                    label = "FAQ",
                    onClick = { navController.navigate("faq") }
                )
            }
        }
    }
}

@Composable
fun ProfileOption(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE8EBF0))
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(28.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium
        )
    }
}